package fr.vinze.textanalysis.svd;

import fr.vinze.textanalysis.matrix.DocumentTokenMatrix;

/**
 * If a {@link DocumentTokenMatrix} contains {@link Double} values, it can be decomposed into singular values.
 * 
 * @author Vinze
 *
 */
public interface SVDBuilder {

	/**
	 * indicates if this builder can work on this specific implementation
	 * 
	 * @param matrix
	 * @return
	 */
	boolean accept(DocumentTokenMatrix<Double> matrix);

	SingularValueDecomposition decompose(DocumentTokenMatrix<Double> matrix);

}
