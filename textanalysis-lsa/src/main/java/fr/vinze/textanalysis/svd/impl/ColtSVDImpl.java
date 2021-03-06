package fr.vinze.textanalysis.svd.impl;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import fr.vinze.textanalysis.svd.SingularValueDecomposition;

public class ColtSVDImpl extends SingularValueDecompositionImpl {

	public ColtSVDImpl(cern.colt.matrix.linalg.SingularValueDecomposition svd, boolean inverted) {
		super(new ColtDoubleMatrixImpl(svd.getU()), new ColtDoubleMatrixImpl(svd.getV()), svd.getSingularValues(),
				new ColtDoubleMatrixImpl((new Algebra()).inverse(svd.getS())), inverted);
	}

	public ColtSVDImpl(cern.colt.matrix.linalg.SingularValueDecomposition svd) {
		super(new ColtDoubleMatrixImpl(svd.getU()), new ColtDoubleMatrixImpl(svd.getV()), svd.getSingularValues(),
				new ColtDoubleMatrixImpl((new Algebra()).inverse(svd.getS())));
	}

	public static class ColtDoubleMatrixImpl implements SingularValueDecomposition.DoubleMatrix {

		DoubleMatrix2D innerMatrix;

		public ColtDoubleMatrixImpl(DoubleMatrix2D innerMatrix) {
			super();
			this.innerMatrix = innerMatrix;
		}

		public int getRawSize() {
			return innerMatrix.rows();
		}

		public int getColSize() {
			return innerMatrix.columns();
		}

		public double getValue(int raw, int col) {
			return innerMatrix.get(raw, col);
		}

		public double[] getRaw(int raw) {
			return innerMatrix.viewRow(raw).toArray();
		}

		public double[] getColumn(int col) {
			return innerMatrix.viewColumn(col).toArray();
		}

		@Override
		public double[][] toArray() {
			return innerMatrix.toArray();
		}
	}

}
