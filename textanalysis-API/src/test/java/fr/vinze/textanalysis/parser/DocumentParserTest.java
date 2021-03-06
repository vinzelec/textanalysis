package fr.vinze.textanalysis.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.parser.impl.TxtDocumentParser;

/**
 * Test the parser mechanism and the txt implementation
 * 
 * @author Vinze
 *
 */
public class DocumentParserTest {

	static Logger log = LoggerFactory.getLogger(DocumentParserTest.class);
	
	@Before
	public void init() throws Exception {
		DocumentParserFactory.clear();
		TxtDocumentParser txtParser = new TxtDocumentParser();
		DocumentParserFactory.registerParser(txtParser);
	}

	@After
	public void close() {
		DocumentParserFactory.clear();
	}

	@Test(timeout = 1000)
	public void testParserAvailable() {
		try {
			DocumentParser parser = DocumentParserFactory.getParser(new File("toto.txt"));
			assertNotNull("parser should not be null", parser);
		} catch (DocumentParserNotAvailable e) {
			log.error("Parser for txt should be loaded", e);
			fail("Parser for txt should be loaded");
		} catch (DocumentTypeNotSupported e) {
			log.error("txt should be supported", e);
			fail("txt should be supported");
		}
	}

	@Test(timeout = 1000)
	public void testParserNotAvailable() {
		try {
			DocumentParserFactory.getParser(new File("toto.xhtml"));
			log.error("Parser for xhtml should not be loaded");
			fail("Parser for xhtml should not be loaded");
		} catch (DocumentParserNotAvailable e) {
			// normal
		} catch (DocumentTypeNotSupported e) {
			log.error("txt should be supported", e);
			fail("txt should be supported");
		}
	}

	@Test(timeout = 1000)
	public void testParserTypeNotSupported() {
		try {
			DocumentParserFactory.getParser(new File("toto.xhtml"));
			log.error("Parser for xhtml should not be loaded");
			fail("Parser for xhtml should not be loaded");
		} catch (DocumentParserNotAvailable e) {
			// normal
		} catch (DocumentTypeNotSupported e) {
			log.error("txt should be supported", e);
			fail("txt should be supported");
		}
	}
	
	@Test(timeout = 1000)
	public void testTxtParser() {
		URL urlToFile = getClass().getResource("/test.txt");
		File testFile = new File(urlToFile.getPath());
		try {
			DocumentParser parser = DocumentParserFactory.getParser(testFile);
			assertNotNull("parser should not be null", parser);
			try {
				RawTextDocument doc = parser.parse(testFile);
				assertNotNull("parsed document should not be null", doc);
				testDocumentContent(doc);
			} catch (FileNotFoundException e) {
				log.error(testFile+" should exist", e);
				fail(testFile+" should exist");
			} catch (ParseException e) {
				log.error(testFile+" should be parsable", e);
				fail(testFile+" should be parsable");
			} catch (IOException e) {
				log.error("unknown IO Exception", e);
			}
		} catch (DocumentParserNotAvailable e) {
			log.error("Parser for txt should be loaded", e);
			fail("Parser for txt should be loaded");
		} catch (DocumentTypeNotSupported e) {
			log.error("txt should be supported", e);
			fail("txt should be supported");
		}
	}
	
	public static void testDocumentContent(RawTextDocument doc) {
		String[] lines = doc.getContent().split("\n");
		assertEquals("the file should contain two lines", 2, lines.length);
		assertEquals("invalid first line content",
				"This is the first line of the test file.", lines[0].trim());
		assertEquals("invalid second line content", "And this is the second.",
				lines[1].trim());
	}

}
