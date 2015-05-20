package fr.vinze.textanalysis.parser.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.document.DocumentTestHelper;
import fr.vinze.textanalysis.document.RawTextDocument;
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
			assertEquals("4 valid xhtml files in the epub", 4, corpus.getSize());
			Collection<String> names = Arrays.asList(new String[] { "00_-_cover.xhtml",
					"01_-_test.xhtml", "02_-_test2.xhtml", "toc.xhtml" });
			for (RawTextDocument document : corpus.getDocuments()) {
				assertTrue("document name [" + document.getName() + "] is not in source folder filenames",
						names.contains(document.getName()));
			}
			testContent(corpus);
		} catch (DocumentParserNotAvailable | DocumentTypeNotSupported | ParseException | IOException e) {
			e.printStackTrace();
			fail("exception caught : " + e.getMessage());
		}
	}

	private void testContent(RawTextDocumentCorpus corpus) {
		RawTextDocument document = corpus.getDocument("00_-_cover.xhtml");
		assertNotNull("document 00_-_1_cover.xhtml should exist in the corpus", document);
		String[] lines = document.getContent().split("\n");
		assertEquals("the file should contain two lines", 2, lines.length);
		assertEquals("invalid first line content", "Test", lines[0].trim());
		assertEquals("invalid second line content", "Vinze Lec", lines[1].trim());
	}

}
