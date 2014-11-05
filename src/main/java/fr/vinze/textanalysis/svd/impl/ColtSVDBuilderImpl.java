package fr.vinze.textanalysis.svd.impl;

import java.security.InvalidParameterException;

import fr.vinze.textanalysis.matrix.DocumentTokenMatrix;
import fr.vinze.textanalysis.matrix.impl.ColtDoubleDocumentTokenMatrix;
import fr.vinze.textanalysis.svd.SVDBuilder;
import fr.vinze.textanalysis.svd.SingularValueDecomposition;

public class ColtSVDBuilderImpl implements SVDBuilder {

	public boolean accept(DocumentTokenMatrix<Double> matrix) {
		return matrix instanceof ColtDoubleDocumentTokenMatrix;
	}

	public SingularValueDecomposition decompose(DocumentTokenMatrix<Double> matrix) {
		if (!accept(matrix)) {
			throw new InvalidParameterException(getClass().getName() + " only works with matrix instance of "
					+ ColtDoubleDocumentTokenMatrix.class.getName());
		}
		ColtDoubleDocumentTokenMatrix coltMatrix = (ColtDoubleDocumentTokenMatrix) matrix;
		cern.colt.matrix.linalg.SingularValueDecomposition svd = new cern.colt.matrix.linalg.SingularValueDecomposition(
				coltMatrix.getInnerMatrix());
		return new ColtSVDImpl(svd, coltMatrix.areDocumentsRows());
	}

}
