package fr.vinze.textanalysis.matrix.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.vinze.textanalysis.corpus.CorpusUtils;
import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.mapper.impl.TokenCounter;
import fr.vinze.textanalysis.matrix.DocumentTokenMatrix;
import fr.vinze.textanalysis.matrix.DocumentTokenMatrixBuilder;

/**
 * Abstract implementation for any {@link DocumentTokenMatrixBuilder} that is built upon one local weight function
 * and one global weight function (such as TF-IDF or log-entropy...)
 * 
 * @see http://en.wikipedia.org/wiki/Latent_semantic_indexing#Term-document_matrix
 * @author Vinze
 *
 */
public abstract class AbstractLocalGlobalMatrixBuilder<V, T extends DocumentTokenMatrix<Double>> implements
		DocumentTokenMatrixBuilder<Double, T> {

	// TODO extract local and global functions to interfaces so this component can be modulable

	private static final Logger log = LoggerFactory.getLogger(AbstractLocalGlobalMatrixBuilder.class);

	protected SegmentedTextDocumentCorpus pretreatment(SegmentedTextDocumentCorpus corpus) {
		// do nothing but can be useful to overwrite
		return corpus;
	}

	protected T posttreatment(T matrix) {
		// do nothing but can be useful to overwrite
		return matrix;
	}

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
		SegmentedTextDocumentCorpus corpus = pretreatment(inputDocuments);
		// initialize matrix using max size possible
		int docCount = corpus.getDocuments().size();
		Set<String> tokens = new HashSet<String>();
		for (SegmentedTextDocument doc : corpus.getDocuments()) {
			for (Token t : doc.getTokens()) {
				tokens.add(t.getUniqueID());
			}
		}
		T matrix = initMatrix(docCount, tokens.size());
		for (SegmentedTextDocument document : corpus.getDocuments()) {
			for (Token token : document.getTokens()) {
				double f = getLocalWeight(token, document);
				double g = getGlobalWeight(token, corpus);
				matrix.setValue(document, token, (f * g));
			}
		}
		return posttreatment(matrix);
	}

	@Override
	public Map<Token, Double> weightQuery(Collection<Token> tokens) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO move to util class or to TokenCounter
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
