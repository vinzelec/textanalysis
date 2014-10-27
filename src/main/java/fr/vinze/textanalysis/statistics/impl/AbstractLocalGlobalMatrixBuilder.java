package fr.vinze.textanalysis.statistics.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.vinze.textanalysis.corpus.CorpusUtils;
import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.mapper.impl.TokenCounter;
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

	private static final Logger log = LoggerFactory.getLogger(AbstractLocalGlobalMatrixBuilder.class);

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

	public static SegmentedTextDocumentCorpus countTokensIfNeeded(final SegmentedTextDocumentCorpus inputDocuments) {
		// check if TokenCounter needs to be run
		SegmentedTextDocument firstDocument = inputDocuments.getDocuments().iterator().next();
		Token firstToken = firstDocument.getTokens().get(0);
		if (firstToken.getMetadata(TokenCounter.COUNT_KEY) == null) {
			log.info("need to run the token counter prior to creating the matrix");
			TokenCounter counter = new TokenCounter();
			return CorpusUtils.map(inputDocuments, counter);
		}
		return inputDocuments;
	}

}
