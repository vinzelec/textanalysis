package fr.vinze.textanalysis.statistics.tfidf;

import org.apache.commons.lang3.mutable.MutableInt;

import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.mapper.impl.TokenCounter;
import fr.vinze.textanalysis.statistics.impl.AbstractLocalGlobalMatrixBuilder;
import fr.vinze.textanalysis.statistics.impl.ColtDoubleMatrix;

/**
 * Build a matrix with score corresponding to TF-IDF algorithm
 * 
 * @see http://en.wikipedia.org/wiki/Tf%E2%80%93idf
 * @author Vinze
 *
 */
public class TFIDFMatrixBuilder extends AbstractLocalGlobalMatrixBuilder<ColtDoubleMatrix> {

	ColtDoubleMatrix matrix = null;

	@Override
	protected ColtDoubleMatrix initMatrix(int initialDocumentSize, int initialTokenSize) {
		matrix = new ColtDoubleMatrix(initialDocumentSize, initialTokenSize);
		return matrix;
	}

	@Override
	protected ColtDoubleMatrix getMatrix() {
		return matrix;
	}

	@Override
	protected SegmentedTextDocumentCorpus pretreatment(SegmentedTextDocumentCorpus corpus) {
		return countTokensIfNeeded(corpus);
	}

	@Override
	protected ColtDoubleMatrix posttreatment(ColtDoubleMatrix matrix) {
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
