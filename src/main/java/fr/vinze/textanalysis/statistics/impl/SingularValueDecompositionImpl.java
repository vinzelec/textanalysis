package fr.vinze.textanalysis.statistics.impl;

import fr.vinze.textanalysis.statistics.SingularValueDecomposition;

public class SingularValueDecompositionImpl implements SingularValueDecomposition {

	DoubleMatrix u, v;
	double[] s;

	public SingularValueDecompositionImpl(DoubleMatrix u, DoubleMatrix v, double[] s) {
		super();
		this.u = u;
		this.v = v;
		this.s = s;
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

}
