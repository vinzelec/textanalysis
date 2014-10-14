package fr.vinze.textanalysis.statistics.impl;

import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.vinze.textanalysis.corpus.CorpusUtils;
import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.mapper.impl.TokenCounter;
import fr.vinze.textanalysis.statistics.DocumentTokenMatrixBuilder;

public class TokenFrequencyMatrixBuilder implements DocumentTokenMatrixBuilder<TokenFrequencyMatrix> {

	private static final Logger log = LoggerFactory.getLogger(TokenFrequencyMatrixBuilder.class);

	// FIXME may need some performance optimization

	private SegmentedTextDocumentCorpus countTokensIfNeeded(final SegmentedTextDocumentCorpus inputDocuments) {
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

	public TokenFrequencyMatrix computeMatrix(final SegmentedTextDocumentCorpus inputDocuments) {
		SegmentedTextDocumentCorpus corpus = countTokensIfNeeded(inputDocuments);
		// initialize matrix using max size possible
		int docCount = corpus.getDocuments().size();
		MutableInt maxTokenCount = new MutableInt(0);
		for (SegmentedTextDocument doc : corpus.getDocuments()) {
			maxTokenCount.add(doc.getTokens().size());
		}
		TokenFrequencyMatrix matrix = new TokenFrequencyMatrix(docCount, maxTokenCount.getValue());
		for (SegmentedTextDocument document : corpus.getDocuments()) {
			for (Token token : document.getTokens()) {
				matrix.setValue(document, token, token.getMetadata(TokenCounter.COUNT_KEY, MutableInt.class).getValue());
			}
		}
		// trim the inner matrix
		matrix.innerMatrix.trimToSize();
		return matrix;
	}

}
