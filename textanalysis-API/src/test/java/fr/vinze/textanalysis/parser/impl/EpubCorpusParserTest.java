package fr.vinze.textanalysis.parser.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.document.DocumentTestHelper;
import fr.vinze.textanalysis.parser.CorpusFactory;
import fr.vinze.textanalysis.parser.CorpusParser;
import fr.vinze.textanalysis.parser.DocumentParserFactory;
import fr.vinze.textanalysis.parser.DocumentParserNotAvailable;
import fr.vinze.textanalysis.parser.DocumentTypeNotSupported;
import fr.vinze.textanalysis.parser.ParseException;

public class EpubCorpusParserTest {

	static File epubFile;

	// TODO move all code from zip to epub when resource available for test

	@BeforeClass
	public static void initResources() {
		URL urlToFile = DocumentTestHelper.class.getResource("/test.epub");
		epubFile = new File(urlToFile.getPath());
		assertTrue("input zip file test.zip must exist", epubFile.exists());
	}

	@Before
	public void init() {
		// document parsers
		DocumentParserFactory.clear();
		XHTMLParser docParser = new XHTMLParser();
		DocumentParserFactory.registerParser(docParser);

		// corpus parsers
		CorpusFactory.clear();
		EPUBCorpusParser corpusParser = new EPUBCorpusParser();
		CorpusFactory.registerParser(corpusParser);
	}

	@Test(timeout = 1000)
	public void test() {
		try {
			CorpusParser corpusParser = CorpusFactory.getParser(epubFile);
			RawTextDocumentCorpus corpus = corpusParser.parseCorpus(epubFile);
			assertEquals("6 valid xhtml files in the epub", 6, corpus.getSize());
			// TODO
// Collection<String> names = Arrays.asList(new String[] { "test.txt", "test2.txt", "test3.text" });
// for (RawTextDocument document : corpus.getDocuments()) {
// assertTrue("document name [" + document.getName() + "] is not in source folder filenames",
// names.contains(document.getName()));
// }
// RawTextDocument document = corpus.getDocument("test.txt");
// assertNotNull("document test.txt should exist in the corpus", document);
// DocumentParserTest.testDocumentContent(document);
		} catch (DocumentParserNotAvailable | DocumentTypeNotSupported | ParseException | IOException e) {
			fail("exception caught : " + e.getMessage());
		}
	}

}
