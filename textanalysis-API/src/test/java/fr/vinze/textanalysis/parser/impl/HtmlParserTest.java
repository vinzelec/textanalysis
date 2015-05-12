package fr.vinze.textanalysis.parser.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.vinze.textanalysis.document.DocumentTestHelper;
import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.parser.DocumentParser;
import fr.vinze.textanalysis.parser.DocumentParserFactory;
import fr.vinze.textanalysis.parser.ParseException;

public class HtmlParserTest {

	static File validHTML, validXHTML, invalidXHTML;

	static final String EXPECTED = "This is the title.\n"
			+ "A sample data to be extracted. Following is the example in tagsoup documentation\n"
			+ "This is bold, bold italic, italic, normal text\n";

	@BeforeClass
	public static void initResources() {
		URL urlToFile = DocumentTestHelper.class.getResource("/html/valid.html");
		validHTML = new File(urlToFile.getPath());
		urlToFile = DocumentTestHelper.class.getResource("/html/valid.xhtml");
		validXHTML = new File(urlToFile.getPath());
		urlToFile = DocumentTestHelper.class.getResource("/html/invalid.xhtml");
		invalidXHTML = new File(urlToFile.getPath());
	}

	@Before
	public void init() {
		DocumentParserFactory.clear();
		DocumentParserFactory.registerParser(new HTMLParser(false));
		DocumentParserFactory.registerParser(new XHTMLParser(false));
	}

	@After
	public void close() {
		DocumentParserFactory.clear();
	}

	private RawTextDocument createAndTestParser(File file) throws Exception {
		assertNotNull(file);
		DocumentParser parser = DocumentParserFactory.getParser(file);
		assertNotNull(parser);
		RawTextDocument document = parser.parse(file);
		assertNotNull(document);
		assertEquals(EXPECTED, document.getContent());
		return document;
	}

	@Test(timeout = 1000)
	public void testHTMLparser() throws Exception {
		createAndTestParser(validHTML);
	}

	@Test(timeout = 1000)
	public void testXHTMLparser() throws Exception {
		createAndTestParser(validXHTML);
	}

	@Test(timeout = 1000)
	public void testInvalidXHTMLparser() throws Exception {
		assertNotNull(invalidXHTML);
		DocumentParser parser = DocumentParserFactory.getParser(invalidXHTML);
		assertNotNull(parser);
		try {
			parser.parse(invalidXHTML);
			fail("invalid document should raise a ParseException");
		} catch (ParseException e) {
			// ok !
		} catch (Exception e) {
			fail("invalid document should raise a ParseException");
		}
	}

	private void testParserWithSourceExtract(File file) throws Exception {
		RawTextDocument document = createAndTestParser(file);
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			String filecontent = IOUtils.toString(is);
			assertEquals(filecontent, document.getRawSource());
		} finally {
			if (is != null) {
				IOUtils.closeQuietly(is);
			}
		}
	}

	@Test(timeout = 1000)
	public void testHTMLparserWithSourceExtract() throws Exception {
		// replace the parser
		DocumentParserFactory.registerParser(new HTMLParser(true));
		testParserWithSourceExtract(validHTML);
	}

	@Test(timeout = 1000)
	public void testXHTMLparserWithSourceExtract() throws Exception {
		// replace the parser
		DocumentParserFactory.registerParser(new XHTMLParser(true));
		testParserWithSourceExtract(validXHTML);
	}

}
