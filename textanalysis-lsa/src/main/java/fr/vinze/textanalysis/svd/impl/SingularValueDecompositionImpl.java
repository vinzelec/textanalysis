package fr.vinze.textanalysis.svd.impl;

import fr.vinze.textanalysis.svd.SingularValueDecomposition;

public class SingularValueDecompositionImpl implements SingularValueDecomposition {

	private final DoubleMatrix u;
	private final DoubleMatrix v;
	private final DoubleMatrix sinversed;
	private final double[] s;
	private final boolean inverted;

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

	@Override
	public int getUSize() {
		return u.getColSize();
	}

	@Override
	public int getVSize() {
		return v.getColSize();
	}

	@Override
	public DoubleMatrix getU() {
		return u;
	}

	@Override
	public DoubleMatrix getV() {
		return v;
	}

	@Override
	public double[] getS() {
		return s;
	}

	@Override
	public DoubleMatrix getSinversed() {
		return sinversed;
	}

	@Override
	public int getRank() {
		return s.length;
	}

	@Override
	public boolean isUDocumentMatrix() {
		return inverted;
	}

}
