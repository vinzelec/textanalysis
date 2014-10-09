package fr.vinze.textanalysis.mapper;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import fr.vinze.textanalysis.document.DocumentTestHelper;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.mapper.impl.KeepOnlyWords;

public class KeepOnlyWordsTest extends TestCase {

	SegmentedTextMapper keepsOnlyWords;
	SegmentedTextDocument sourceDoc;

	@Override
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		keepsOnlyWords = new KeepOnlyWords();
		sourceDoc = DocumentTestHelper.createTestSegmentedDocument();
	}

	@Test
	public void testMapper() throws Exception {
		assertEquals("segmented document before mapper should have 19 tokens", 19, sourceDoc.getTokens().size());
		SegmentedTextDocument targetDoc = keepsOnlyWords.map(sourceDoc);
		assertEquals("segmented document after mapper should have 14 tokens", 14, targetDoc.getTokens().size());
		for (Token token : targetDoc.getTokens()) {
			assertTrue("every token is a word", token instanceof Word);
		}
	}

}
