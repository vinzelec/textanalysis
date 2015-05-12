package fr.vinze.textanalysis.matrix.logentropy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableInt;

import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.impl.MetadataImpl;
import fr.vinze.textanalysis.document.impl.SegmentedTextDocumentImpl;
import fr.vinze.textanalysis.mapper.SegmentedTextMapper;
import fr.vinze.textanalysis.mapper.impl.TokenCounter;

/**
 * mapper specific to do the same as {@link TokenCounter} but collect additional
 * information necessary for the entropy function of {@link LogEntropyMatrixBuilder}
 * 
 * @author Vinze
 *
 */
public class EntropyPretreatment implements SegmentedTextMapper {

	public static final String GLOBAL_COUNT_KEY = EntropyPretreatment.class.getName() + "token_global_count";

	final SegmentedTextDocumentCorpus corpus;
	Map<String, MutableInt> globalCount;

	public EntropyPretreatment(SegmentedTextDocumentCorpus corpus) {
		super();
		this.corpus = corpus;
		globalCount = new HashMap<String, MutableInt>();
	}

	// TODO refactor code with TokenCounter

	public SegmentedTextDocument map(SegmentedTextDocument document) {
		MutableInt documentTokensCount = new MutableInt(0);
		List<Token> outputTokenList = new ArrayList<Token>();
		// a cache of the count not to have to
		Map<Token, MutableInt> countcache = new HashMap<Token, MutableInt>();
		for (Token inputToken : document.getTokens()) {
			documentTokensCount.increment();
			Token outputToken = null;
			MutableInt tokenCounter = null;
			if (!outputTokenList.contains(inputToken)) {
				outputToken = inputToken;
				tokenCounter = new MutableInt(0);
				outputTokenList.add(outputToken);
				countcache.put(outputToken, tokenCounter);
				outputToken.addMetadata(new MetadataImpl<MutableInt>(TokenCounter.COUNT_KEY, tokenCounter));
			} else {
				int idx = outputTokenList.indexOf(inputToken);
				outputToken = outputTokenList.get(idx);
				outputToken.mergeMetadata(inputToken);
				tokenCounter = countcache.get(outputToken);
			}
			tokenCounter.increment();
			// specific entropy adding
			MutableInt tokenGlobalCounter = null;
			if (globalCount.containsKey(outputToken.toString())) {
				tokenGlobalCounter = globalCount.get(outputToken.toString());
			} else {
				tokenGlobalCounter = new MutableInt(0);
				globalCount.put(outputToken.toString(), tokenGlobalCounter);
			}
			tokenGlobalCounter.increment();
			outputToken.addMetadata(new MetadataImpl<MutableInt>(GLOBAL_COUNT_KEY, tokenGlobalCounter));
		}
		SegmentedTextDocumentImpl newDocument = new SegmentedTextDocumentImpl(document.getName(), document.getSource());
		newDocument.setTokens(outputTokenList);
		newDocument.setTokenCount(documentTokensCount.intValue());
		return newDocument;
	}

}
