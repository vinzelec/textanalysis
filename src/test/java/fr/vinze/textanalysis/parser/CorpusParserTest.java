package fr.vinze.textanalysis.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.parser.impl.DirectoryCorpusParser;
import fr.vinze.textanalysis.parser.impl.TxtDocumentParser;

public class CorpusParserTest extends TestCase {

	static Logger log = LoggerFactory.getLogger(CorpusParserTest.class);

	@Before
	protected void setUp() throws Exception {
		// document parsers
		DocumentParserFactory.clear();
		TxtDocumentParser txtParser = new TxtDocumentParser();
		DocumentParserFactory.registerParser(txtParser);

		// corpus parsers
		CorpusFactory.clear();
		DirectoryCorpusParser corpusParser = new DirectoryCorpusParser();
		CorpusFactory.registerParser(corpusParser);
	}

	@Test
	public void testParserAvailable() {
		try {
			CorpusParser corpusParser = CorpusFactory.getParser(new File(
					"testFolder"));
			assertNotNull(
					"corpus parser for text folder should be available and not null",
					corpusParser);
		} catch (DocumentParserNotAvailable e) {
			log.error("corpus parser for text folder should be available", e);
			fail("corpus parser for text folder should be available");
		} catch (DocumentTypeNotSupported e) {
			log.error("text folder should be a supported type of corpus", e);
			fail("text folder should be a supported type of corpus");
		}
	}

	@Test
	public void testParserNotAvailable() {
		try {
			CorpusFactory.getParser(new File("test.zip"));
			fail("no parser should be available for zip files");
		} catch (DocumentParserNotAvailable e) {
			// ok test passed
		} catch (DocumentTypeNotSupported e) {
			log.error("zip should be a supported type of corpus", e);
			fail("zip should be a supported type of corpus");
		}
	}

	@Test
	public void testParserTypeNotSupported() {
		try {
			CorpusFactory.getParser(new File("test.toto"));
			fail("no parser should be available for toto files");
		} catch (DocumentParserNotAvailable e) {
			log.error("toto should not be a supported type of corpus", e);
			fail("toto should not be a supported type of corpus");
		} catch (DocumentTypeNotSupported e) {
			// ok test passed
		}
	}

	@Test
	public void testDirectoryParser() {
		URL urlToFile = getClass().getResource("/testFolder");
		File folder = new File(urlToFile.getPath());
		try {
			CorpusParser corpusParser = CorpusFactory.getParser(folder);
			assertNotNull(
					"corpus parser for text folder should be available and not null",
					corpusParser);
			RawTextDocumentCorpus corpus = corpusParser.parseCorpus(folder);
			assertNotNull("corpus should not be null", corpus);
			assertEquals("corpus should contain 3 texts", 3, corpus.getSize());
			Collection<String> names = Arrays.asList(new String[] { "test.txt",
					"test2.txt", "test3.text" });
			for (RawTextDocument document : corpus.getDocuments()) {
				assertTrue("document name [" + document.getName()
						+ "] is not in source folder filenames",
						names.contains(document.getName()));
			}
			RawTextDocument document = corpus.getDocument("test.txt");
			assertNotNull("document test.txt should exist in the corpus",
					document);
			DocumentParserTest.testDocumentContent(document);
		} catch (DocumentParserNotAvailable e) {
			log.error("corpus parser for text folder should be available", e);
			fail("corpus parser for text folder should be available");
		} catch (DocumentTypeNotSupported e) {
			log.error("text folder should be a supported type of corpus", e);
			fail("text folder should be a supported type of corpus");
		} catch (FileNotFoundException e) {
			log.error(folder + " should exists", e);
			fail(folder + " should exists");
		} catch (ParseException e) {
			log.error(folder + " should be correctly parsed", e);
			fail(folder + " should be correctly parsed");
		} catch (IOException e) {
			log.error("unexpected IO exception", e);
			fail("unexpected IO exception");
		}
	}

}
