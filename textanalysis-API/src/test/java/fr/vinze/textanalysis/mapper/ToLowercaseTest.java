package fr.vinze.textanalysis.mapper;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.mapper.impl.SegmentedToLowercase;
import fr.vinze.textanalysis.mapper.impl.ToLowercase;
import fr.vinze.textanalysis.segmentation.Splitter;
import fr.vinze.textanalysis.segmentation.impl.TextSplitterImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ToLowercaseTest {

	public static final String INPUT = "This Is A TesT !";

	public static final String EXPECTED = "this is a test !";

	RawTextDocument doc;
	ToLowercase tolower;
	SegmentedToLowercase segmentedtolower;

	@Before
	public void init() {
		doc = new RawTextDocumentImpl("doc", INPUT);
		tolower = new ToLowercase();
		segmentedtolower = new SegmentedToLowercase();

	}

	@Test(timeout = 1000)
	public void testOnRawTextDocument() throws Exception {
		RawTextDocument outputDoc = tolower.apply(doc);
		assertEquals(EXPECTED, outputDoc.getContent());
	}

	@Test(timeout = 1000)
	public void testOnSegmentedDocument() throws Exception {
		Splitter defaultSplitter = new TextSplitterImpl();
		// before "to lower case"
		SegmentedTextDocument input = defaultSplitter.apply(doc);
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
		SegmentedTextDocument output = segmentedtolower.apply(input);
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
