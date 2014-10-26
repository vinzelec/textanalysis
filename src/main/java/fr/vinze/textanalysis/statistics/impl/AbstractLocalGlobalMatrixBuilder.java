package fr.vinze.textanalysis.statistics.impl;

import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.statistics.DocumentTokenMatrix;
import fr.vinze.textanalysis.statistics.DocumentTokenMatrixBuilder;

/**
 * Abstract implementation for any {@link DocumentTokenMatrixBuilder} that is built upon one local weight function
 * and one global weight function (such as TF-IDF or log-entropy...)
 * 
 * @author Vinze
 *
 */
public abstract class AbstractLocalGlobalMatrixBuilder<T extends DocumentTokenMatrix<Double>> implements
		DocumentTokenMatrixBuilder<T> {

	/**
	 * create the matrix and returns it
	 * 
	 * @param initialDocumentSize
	 * @param initialTokenSize
	 * @return
	 */
	protected abstract T initMatrix(int initialDocumentSize, int initialTokenSize);

	/**
	 * @return the matrix being built.
	 */
	protected abstract T getMatrix();

	/**
	 * Compute the local score for a token in a specific document
	 * 
	 * @param token
	 * @param document
	 * @return
	 */
	protected abstract double getLocalWeight(Token token, SegmentedTextDocument document);
	
	/**
	 * Compute the global score for a token in the corpus
	 * 
	 * @param token
	 * @param corpus
	 * @return
	 */
	protected abstract double getGlobalWeight(Token token, SegmentedTextDocumentCorpus corpus);
	
	public T computeMatrix(SegmentedTextDocumentCorpus inputDocuments) {
		// TODO TFIDFMatrixBuilder
		return null;
	}

}
