package fr.vinze.textanalysis.matrix.tfidf;

import org.apache.commons.lang3.mutable.MutableInt;

import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.mapper.impl.TokenCounter;
import fr.vinze.textanalysis.matrix.impl.AbstractLocalGlobalMatrixBuilder;
import fr.vinze.textanalysis.matrix.impl.ColtDoubleDocumentTokenMatrix;

/**
 * Build a matrix with score corresponding to TF-IDF algorithm
 * 
 * @see http://en.wikipedia.org/wiki/Tf%E2%80%93idf
 * @author Vinze
 *
 */
public class TFIDFMatrixBuilder extends AbstractLocalGlobalMatrixBuilder<Double, ColtDoubleDocumentTokenMatrix> {

	ColtDoubleDocumentTokenMatrix matrix = null;

	@Override
	protected ColtDoubleDocumentTokenMatrix initMatrix(int initialDocumentSize, int initialTokenSize) {
		matrix = new ColtDoubleDocumentTokenMatrix(initialDocumentSize, initialTokenSize);
		return matrix;
	}

	@Override
	protected ColtDoubleDocumentTokenMatrix getMatrix() {
		return matrix;
	}

	@Override
	protected SegmentedTextDocumentCorpus pretreatment(SegmentedTextDocumentCorpus corpus) {
		return countTokensIfNeeded(corpus);
	}

	@Override
	protected ColtDoubleDocumentTokenMatrix posttreatment(
			@SuppressWarnings("hiding") ColtDoubleDocumentTokenMatrix matrix) {
		matrix.getInnerMatrix().trimToSize();
		return matrix;
	}

	@Override
	protected double getLocalWeight(Token token, SegmentedTextDocument document) {
		// TF : only the term count
		double count = 0;
		for (Token otherToken : document.getTokens()) {
			if (otherToken.equals(token)) {
				count = token.getMetadata(TokenCounter.COUNT_KEY, MutableInt.class).getValue().doubleValue();
				break;
			}
		}
		return count;
	}

	@Override
	protected double getGlobalWeight(Token token, SegmentedTextDocumentCorpus corpus) {
		// DF : log(#docs / #docs with token)
		double docCount = corpus.getSize();
		MutableInt containsCount = new MutableInt();
		for (SegmentedTextDocument doc : corpus.getDocuments()) {
			if (doc.getTokens().contains(token)) {
				containsCount.increment();
			}
		}
		return Math.log10(docCount / containsCount.doubleValue());
	}

}
