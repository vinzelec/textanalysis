package fr.vinze.textanalysis.statistics.logentropy;

import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableInt;

import fr.vinze.textanalysis.corpus.CorpusUtils;
import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.impl.MetadataImpl;
import fr.vinze.textanalysis.mapper.impl.TokenCounter;
import fr.vinze.textanalysis.statistics.impl.AbstractLocalGlobalMatrixBuilder;
import fr.vinze.textanalysis.statistics.impl.ColtDoubleMatrix;

/**
 * Build a matrix with score corresponding to log-entropy algorithm
 * 
 * @see http://en.wikipedia.org/wiki/Latent_semantic_indexing
 * @author Vinze
 *
 */
public class LogEntropyMatrixBuilder extends AbstractLocalGlobalMatrixBuilder<ColtDoubleMatrix> {

	public static final String TOKENWEIGHT_KEY = LogEntropyMatrixBuilder.class.getName() + "TW";

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
		return CorpusUtils.map(corpus, new EntropyPretreatment(corpus));
	}

	@Override
	protected ColtDoubleMatrix posttreatment(ColtDoubleMatrix matrix) {
		matrix.getInnerMatrix().trimToSize();
		return matrix;
	}

	@Override
	protected double getLocalWeight(Token token, SegmentedTextDocument document) {
		double count = 0;
		for (Token otherToken : document.getTokens()) {
			if (otherToken.equals(token)) {
				count = token.getMetadata(TokenCounter.COUNT_KEY, MutableInt.class).getValue().doubleValue();
				break;
			}
		}
		return Math.log(count + 1.0);
	}

	// TODO result for a token can be reused

	@Override
	protected double getGlobalWeight(Token token, SegmentedTextDocumentCorpus corpus) {
		if (token.getMetadata(TOKENWEIGHT_KEY) == null) {
			MutableDouble sum = new MutableDouble();
			double logn = Math.log(corpus.getSize());
			double gfi = token.getMetadata(EntropyPretreatment.GLOBAL_COUNT_KEY, MutableInt.class).getValue()
					.doubleValue();
			for (SegmentedTextDocument document : corpus.getDocuments()) {
				// get similar token with it's own metadata relative to this document
				// if token is not in the document nothing to add
				if (document.getTokens().contains(token)) {
					Token localToken = null;
					for (Token t : document.getTokens()) {
						if (t.equals(token)) {
							localToken = t;
							break;
						}
					}
					double tfij = localToken.getMetadata(TokenCounter.COUNT_KEY, MutableInt.class).getValue()
							.doubleValue();
					double pij = tfij / gfi;
					double logpij = Math.log(pij);
					sum.add(pij * logpij / logn);
				}
			}
			token.addMetadata(new MetadataImpl<Double>(TOKENWEIGHT_KEY, 1.0 + sum.doubleValue()));
		}
		return token.getMetadata(TOKENWEIGHT_KEY, Double.class).getValue();
	}

}
