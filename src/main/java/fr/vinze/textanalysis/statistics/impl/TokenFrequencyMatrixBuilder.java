package fr.vinze.textanalysis.statistics.impl;

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

	public TokenFrequencyMatrix computeMatrix(SegmentedTextDocumentCorpus inputDocuments) {
		// check if TokenCounter needs to be run
		SegmentedTextDocument firstDocument = inputDocuments.getDocuments().iterator().next();
		Token firstToken = firstDocument.getTokens().get(0);
		if (firstToken.getMetadata(TokenCounter.COUNT_KEY) == null) {
			log.info("need to run the token counter prior to creating the matrix");
			TokenCounter counter = new TokenCounter();
			inputDocuments = CorpusUtils.map(inputDocuments, counter);
		}
		// TODO Auto-generated method stub
		return null;
	}

}
