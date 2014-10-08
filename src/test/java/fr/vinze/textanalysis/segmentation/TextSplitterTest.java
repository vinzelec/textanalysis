package fr.vinze.textanalysis.segmentation;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.parser.DocumentParser;
import fr.vinze.textanalysis.parser.DocumentParserFactory;
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
		// TODO
	}

}
