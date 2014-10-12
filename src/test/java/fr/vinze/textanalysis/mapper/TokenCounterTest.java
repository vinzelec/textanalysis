package fr.vinze.textanalysis.mapper;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import fr.vinze.textanalysis.document.DocumentTestHelper;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
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
		// TODO
	}

}
