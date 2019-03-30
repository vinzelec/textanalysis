package fr.vinze.textanalysis.mapper.impl;

import fr.vinze.textanalysis.corpus.CorpusUtils;
import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.impl.MetadataImpl;
import fr.vinze.textanalysis.document.impl.SegmentedTextDocumentImpl;
import fr.vinze.textanalysis.mapper.SegmentedTextMapper;
import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenCounter implements SegmentedTextMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenCounter.class);

	public static final String COUNT_KEY = TokenCounter.class.getName() + "token_count";

	@Override
	public SegmentedTextDocument apply(SegmentedTextDocument document) {
		MutableInt tokenCount = new MutableInt(0);
		List<Token> outputTokenList = new ArrayList<>();
		// a cache of the count not to have to
		Map<Token, MutableInt> countcache = new HashMap<>();
		for (Token inputToken : document.getTokens()) {
			tokenCount.increment();
			Token outputToken = null;
			MutableInt counter = null;
			if (!outputTokenList.contains(inputToken)) {
				outputToken = inputToken;
				counter = new MutableInt(0);
				outputTokenList.add(outputToken);
				countcache.put(outputToken, counter);
				outputToken.addMetadata(new MetadataImpl<>(COUNT_KEY, counter));
			} else {
				int idx = outputTokenList.indexOf(inputToken);
				outputToken = outputTokenList.get(idx);
				outputToken.mergeMetadata(inputToken);
				counter = countcache.get(outputToken);
			}
			counter.increment();
		}
		SegmentedTextDocumentImpl newDocument = new SegmentedTextDocumentImpl(document.getName(), document.getSource());
		newDocument.setTokens(outputTokenList);
		newDocument.setTokenCount(tokenCount.intValue());
		return newDocument;
	}

	public static SegmentedTextDocumentCorpus countTokensIfNeeded(final SegmentedTextDocumentCorpus inputDocuments) {
		// check if TokenCounter needs to be run
		SegmentedTextDocument firstDocument = inputDocuments.getDocuments().iterator().next();
		Token firstToken = firstDocument.getTokens().get(0);
		if (firstToken.getMetadata(TokenCounter.COUNT_KEY) == null) {
			LOGGER.info("need to run the token counter prior to creating the matrix");
			TokenCounter counter = new TokenCounter();
			return CorpusUtils.map(inputDocuments, counter);
		}
		return inputDocuments;
	}
}
