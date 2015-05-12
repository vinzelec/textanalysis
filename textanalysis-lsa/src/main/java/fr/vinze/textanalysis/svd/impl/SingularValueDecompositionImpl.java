package fr.vinze.textanalysis.svd.impl;

import fr.vinze.textanalysis.svd.SingularValueDecomposition;

public class SingularValueDecompositionImpl implements SingularValueDecomposition {

	DoubleMatrix u, v;
	double[] s;
	boolean inverted;

	public SingularValueDecompositionImpl(DoubleMatrix u, DoubleMatrix v, double[] s, boolean inverted) {
		super();
		this.u = u;
		this.v = v;
		this.s = s;
		this.inverted = inverted;
	}

	public SingularValueDecompositionImpl(DoubleMatrix u, DoubleMatrix v, double[] s) {
		this(u, v, s, false);
	}

	public int getUSize() {
		return u.getColSize();
	}

	public int getVSize() {
		return v.getColSize();
	}

	public DoubleMatrix getU() {
		return u;
	}

	public DoubleMatrix getV() {
		return v;
	}

	public double[] getS() {
		return s;
	}

	public int getRank() {
		return s.length;
	}

	public boolean isUDocumentMatrix() {
		return inverted;
	}

}
