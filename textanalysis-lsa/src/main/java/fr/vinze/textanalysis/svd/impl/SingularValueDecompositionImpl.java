package fr.vinze.textanalysis.svd.impl;

import fr.vinze.textanalysis.svd.SingularValueDecomposition;

public class SingularValueDecompositionImpl implements SingularValueDecomposition {

	DoubleMatrix u, v, sinversed;
	double[] s;
	boolean inverted;

	public SingularValueDecompositionImpl(DoubleMatrix u, DoubleMatrix v, double[] s, DoubleMatrix sinversed,
			boolean inverted) {
		super();
		this.u = u;
		this.v = v;
		this.s = s;
		this.sinversed = sinversed;
		this.inverted = inverted;
	}

	public SingularValueDecompositionImpl(DoubleMatrix u, DoubleMatrix v, double[] s, DoubleMatrix sinversed) {
		this(u, v, s, sinversed, false);
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

	public DoubleMatrix getSinversed() {
		return sinversed;
	}

	public int getRank() {
		return s.length;
	}

	public boolean isUDocumentMatrix() {
		return inverted;
	}

}
