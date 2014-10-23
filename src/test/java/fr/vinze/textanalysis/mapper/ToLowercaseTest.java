package fr.vinze.textanalysis.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.mapper.impl.ToLowercase;
import fr.vinze.textanalysis.segmentation.Splitter;
import fr.vinze.textanalysis.segmentation.impl.TextSplitterImpl;

public class ToLowercaseTest {

	public static final String INPUT = "This Is A TesT !";

	public static final String EXPECTED = "this is a test !";

	RawTextDocument doc;
	ToLowercase tolower;

	@Before
	public void init() {
		doc = new RawTextDocumentImpl("doc", INPUT);
		tolower = new ToLowercase();

	}

	@Test(timeout = 1000)
	public void testOnRawTextDocument() throws Exception {
		RawTextDocument outputDoc = tolower.map(doc);
		assertEquals(EXPECTED, outputDoc.getContent());
	}

	@Test(timeout = 1000)
	public void testOnSegmentedDocument() throws Exception {
		Splitter defaultSplitter = new TextSplitterImpl();
		// before "to lower case"
		SegmentedTextDocument input = defaultSplitter.split(doc);
		List<Token> tokens = input.getTokens();
		Token tok = tokens.get(0);
		assertTrue("[input] instanceof on token 0", tok instanceof Word);
		assertEquals("[input] equals on token 0", "This", ((Word) tok).getWord());
		tok = tokens.get(1);
		assertTrue("[input] instanceof on token 1", tok instanceof Word);
		assertEquals("[input] equals on token 1", "Is", ((Word) tok).getWord());
		tok = tokens.get(2);
		assertTrue("[input] instanceof on token 2", tok instanceof Word);
		assertEquals("[input] equals on token 2", "A", ((Word) tok).getWord());
		tok = tokens.get(3);
		assertTrue("[input] instanceof on token 3", tok instanceof Word);
		assertEquals("[input] equals on token 3", "TesT", ((Word) tok).getWord());
		tok = tokens.get(4);
		assertFalse("[input] instanceof on token 0", tok instanceof Word);
		// after "to lower case"
		SegmentedTextDocument output = tolower.map(input);
		tokens = output.getTokens();
		tok = tokens.get(0);
		assertTrue("[output] instanceof on token 0", tok instanceof Word);
		assertEquals("[output] equals on token 0", "this", ((Word) tok).getWord());
		tok = tokens.get(1);
		assertTrue("[output] instanceof on token 1", tok instanceof Word);
		assertEquals("[output] equals on token 1", "is", ((Word) tok).getWord());
		tok = tokens.get(2);
		assertTrue("[output] instanceof on token 2", tok instanceof Word);
		assertEquals("[output] equals on token 2", "a", ((Word) tok).getWord());
		tok = tokens.get(3);
		assertTrue("[output] instanceof on token 3", tok instanceof Word);
		assertEquals("[output] equals on token 3", "test", ((Word) tok).getWord());
		tok = tokens.get(4);
		assertFalse("[output] instanceof on token 0", tok instanceof Word);
	}

}
