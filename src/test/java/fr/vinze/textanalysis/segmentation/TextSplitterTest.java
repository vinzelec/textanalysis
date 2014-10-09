package fr.vinze.textanalysis.segmentation;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import fr.vinze.textanalysis.document.Punctuation;
import fr.vinze.textanalysis.document.Punctuation.PunctuationMark;
import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.document.impl.SpecialToken;
import fr.vinze.textanalysis.document.impl.SpecialToken.TokenType;
import fr.vinze.textanalysis.parser.DocumentParser;
import fr.vinze.textanalysis.parser.DocumentParserFactory;
import fr.vinze.textanalysis.parser.DocumentParserTest;
import fr.vinze.textanalysis.parser.impl.TxtDocumentParser;
import fr.vinze.textanalysis.segmentation.impl.TextSplitterImpl;

public class TextSplitterTest extends TestCase {

	Splitter splitter;
	RawTextDocument sourceDocument;

	@Override
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		// init splitter
		splitter = new TextSplitterImpl();
		// parse a document
		DocumentParserFactory.registerParser(new TxtDocumentParser());
		URL urlToFile = getClass().getResource("/test.txt");
		File testFile = new File(urlToFile.getPath());
		DocumentParser parser = DocumentParserFactory.getParser(testFile);
		assertNotNull("parser should not be null", parser);
		sourceDocument = parser.parse(testFile);
		assertNotNull("parsed document should not be null", sourceDocument);
	}

	@Test
	public void testSplitter() {
		// need to make sure of text content
		DocumentParserTest.testDocumentContent(sourceDocument);
		SegmentedTextDocument segmentedDocument = splitter.split(sourceDocument);
		assertNotNull("segmented document should not be null", segmentedDocument);
		assertEquals("segmented document should have the same name as source document", sourceDocument.getName(),
				segmentedDocument.getName());
		assertEquals("segmented document should have the source document as source", sourceDocument,
				segmentedDocument.getSource());
		assertNotNull("segmented document should have a token list", segmentedDocument.getTokens());
		assertEquals("segmented document should have 19 tokens", 19, segmentedDocument.getTokens().size());
		// not testing all
		assertTrue("first element is a word", segmentedDocument.getTokens().get(0) instanceof Word);
		assertEquals("first element is 'This'", "This", ((Word) segmentedDocument.getTokens().get(0)).getWord());
		assertTrue("10th element is a punctuation", segmentedDocument.getTokens().get(9) instanceof Punctuation);
		assertEquals("10th element is a dotc", PunctuationMark.DOT,
				((Punctuation) segmentedDocument.getTokens().get(9)).getPunctuationMark());
		assertTrue("11th element is a special token", segmentedDocument.getTokens().get(10) instanceof SpecialToken);
		assertEquals("11th element is an end of paragraph", TokenType.END_OF_PARAGRAPH,
				((SpecialToken) segmentedDocument.getTokens().get(10)).getType());
		assertTrue("last element is a special token", segmentedDocument.getTokens().get(18) instanceof SpecialToken);
		assertEquals("last element is end of document", TokenType.END_OF_DOCUMENT, ((SpecialToken) segmentedDocument
				.getTokens().get(18)).getType());
	}

}
