package fr.vinze.textanalysis.statistics.svd;

/**
 * <p>
 * A matrix decomposed as two square matrix U and V and a set of singular values &Sigma;. So that initial matrix
 * <code>M = U&Sigma;V*</code>.
 * </p>
 * 
 * @author Vinze
 *
 */
public interface SingularValueDecomposition {

	int getUSize();

	int getVSize();

	DoubleMatrix getU();

	DoubleMatrix getV();

	double[] getS();

	/**
	 * Interface for a 2D double matrix...
	 * 
	 * @author Vinze
	 *
	 */
	public static interface DoubleMatrix {

		int getRawSize();

		int getColSize();

		double getValue(int raw, int col);

		double[] getRaw(int raw);

		double[] getColumn(int col);
	}

}
