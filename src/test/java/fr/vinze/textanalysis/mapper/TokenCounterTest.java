package fr.vinze.textanalysis.mapper;

import junit.framework.TestCase;

import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Before;
import org.junit.Test;

import fr.vinze.textanalysis.document.DocumentTestHelper;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.Token.Metadata;
import fr.vinze.textanalysis.mapper.impl.TokenCounter;

public class TokenCounterTest extends TestCase {

	SegmentedTextMapper tokencounter;
	SegmentedTextDocument inputDocument;

	@Override
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		tokencounter = new TokenCounter();
		inputDocument = DocumentTestHelper.createTestSegmentedDocument();
		assertNotNull(inputDocument);
	}

	@Test
	public void testMapper() throws Exception {
		SegmentedTextDocument outputDocument = tokencounter.map(inputDocument);
		// First token "This" (only once as case sensitive)
		Token token = outputDocument.getTokens().get(0);
		assertNotNull("token has a count metadata", token.getMetadata(TokenCounter.COUNT_KEY));
		Metadata<MutableInt> count = token.getMetadata(TokenCounter.COUNT_KEY, MutableInt.class);
		assertEquals("token should be present 1 time", 1, count.getValue().intValue());
		// second token "is" (twice in document)
		token = outputDocument.getTokens().get(1);
		assertNotNull("token has a count metadata", token.getMetadata(TokenCounter.COUNT_KEY));
		count = token.getMetadata(TokenCounter.COUNT_KEY, MutableInt.class);
		assertEquals("token should be present 2 times", 2, count.getValue().intValue());
	}

}
