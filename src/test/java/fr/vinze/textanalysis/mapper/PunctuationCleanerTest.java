package fr.vinze.textanalysis.mapper;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.mapper.impl.PunctuationCleaner;

public class PunctuationCleanerTest extends TestCase {

	final static String NAME = "documentName";
	final static String CONTENT = "This «test» is to be cleant...";
	final static String EXPECTED = "This \"test\" is to be cleant…";
	RawTextDocument source;
	RawTextMapper punctuationCleaner;

	@Override
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		source = new RawTextDocumentImpl(NAME, CONTENT);
		punctuationCleaner = new PunctuationCleaner();
	}

	@Test
	public void test() {
		RawTextDocument target = punctuationCleaner.map(source);
		assertEquals(
				"name of target document should be the same as source one",
				source.getName(), target.getName());
		assertEquals("content of target document is not what expected",
				EXPECTED, target.getContent());

	}

}
