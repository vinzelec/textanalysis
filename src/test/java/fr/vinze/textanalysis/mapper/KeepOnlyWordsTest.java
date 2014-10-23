package fr.vinze.textanalysis.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import fr.vinze.textanalysis.document.DocumentTestHelper;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.mapper.impl.KeepOnlyWords;

public class KeepOnlyWordsTest {

	SegmentedTextMapper keepsOnlyWords;
	SegmentedTextDocument sourceDoc;

	@Before
	public void init() throws Exception {
		keepsOnlyWords = new KeepOnlyWords();
		sourceDoc = DocumentTestHelper.createTestSegmentedDocument();
	}

	@Test(timeout = 1000)
	public void testMapper() throws Exception {
		assertEquals("segmented document before mapper should have 19 tokens", 19, sourceDoc.getTokens().size());
		SegmentedTextDocument targetDoc = keepsOnlyWords.map(sourceDoc);
		assertEquals("segmented document after mapper should have 14 tokens", 14, targetDoc.getTokens().size());
		for (Token token : targetDoc.getTokens()) {
			assertTrue("every token is a word", token instanceof Word);
		}
	}

}
