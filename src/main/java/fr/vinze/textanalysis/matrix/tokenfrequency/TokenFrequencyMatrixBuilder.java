package fr.vinze.textanalysis.matrix.tokenfrequency;

import org.apache.commons.lang3.mutable.MutableInt;

import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.mapper.impl.TokenCounter;
import fr.vinze.textanalysis.matrix.DocumentTokenMatrixBuilder;
import fr.vinze.textanalysis.matrix.impl.AbstractLocalGlobalMatrixBuilder;

public class TokenFrequencyMatrixBuilder implements DocumentTokenMatrixBuilder<TokenFrequencyMatrix> {

	// FIXME may need some performance optimization

	public TokenFrequencyMatrix computeMatrix(final SegmentedTextDocumentCorpus inputDocuments) {
		SegmentedTextDocumentCorpus corpus = AbstractLocalGlobalMatrixBuilder.countTokensIfNeeded(inputDocuments);
		// initialize matrix using max size possible
		int docCount = corpus.getDocuments().size();
		MutableInt maxTokenCount = new MutableInt(0);
		for (SegmentedTextDocument doc : corpus.getDocuments()) {
			maxTokenCount.add(doc.getTokenCount());
		}
		TokenFrequencyMatrix matrix = new TokenFrequencyMatrix(docCount, maxTokenCount.getValue());
		for (SegmentedTextDocument document : corpus.getDocuments()) {
			for (Token token : document.getTokens()) {
				matrix.setValue(document, token, token.getMetadata(TokenCounter.COUNT_KEY, MutableInt.class).getValue());
			}
		}
		// trim the inner matrix
		matrix.getInnerMatrix().trimToSize();
		return matrix;
	}

}
