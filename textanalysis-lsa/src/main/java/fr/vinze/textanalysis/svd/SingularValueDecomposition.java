package fr.vinze.textanalysis.svd;

import fr.vinze.textanalysis.lsa.SemanticSpace;

/**
 * <p>
 * A matrix decomposed as two matrix U and V and a set of singular values &Sigma;. So that initial matrix
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

	DoubleMatrix getSinversed(); // matrix inverse of singular values

	int getRank();

	/**
	 * in default, U is the term matrix and V the document matrix.
	 * If <code>true</code> it means that there are less terms than documents
	 * and role are inverted...
	 * FIXME this should be an information of {@link SemanticSpace} not {@link SingularValueDecomposition}
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

		double[][] toArray();
	}

}
