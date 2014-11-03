package fr.vinze.textanalysis.statistics.svd;

/**
 * <p>
 * A matrix decomposed as two square matrix U and V and a set of singular values &Sigma;. So that initial matrix
 * <code>M = U&Sigma;V*</code>.
 * </p>
 * FIXME find a way to determine which of U and V is the document matrix and the token one.
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

	int getRank();

	/**
	 * in default, U is the term matrix and V the document matrix.
	 * If <code>true</code> it means that there are less terms than documents
	 * and role are inverted...
	 * 
	 * @return
	 */
	boolean isUDocumentMatrix();

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
