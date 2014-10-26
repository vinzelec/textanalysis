package fr.vinze.textanalysis.mapper.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableInt;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.impl.MetadataImpl;
import fr.vinze.textanalysis.document.impl.SegmentedTextDocumentImpl;
import fr.vinze.textanalysis.mapper.SegmentedTextMapper;

public class TokenCounter implements SegmentedTextMapper {

	public static final String COUNT_KEY = "token_count";

	public SegmentedTextDocument map(SegmentedTextDocument document) {
		MutableInt tokenCount = new MutableInt(0);
		List<Token> outputTokenList = new ArrayList<Token>();
		// a cache of the count not to have to
		Map<Token, MutableInt> countcache = new HashMap<Token, MutableInt>();
		for (Token inputToken : document.getTokens()) {
			tokenCount.increment();
			Token outputToken = null;
			MutableInt counter = null;
			if (!outputTokenList.contains(inputToken)) {
				outputToken = inputToken;
				counter = new MutableInt(0);
				outputTokenList.add(outputToken);
				countcache.put(outputToken, counter);
				outputToken.addMetadata(new MetadataImpl<MutableInt>(COUNT_KEY, counter));
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

}
