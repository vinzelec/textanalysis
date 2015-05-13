package fr.vinze.textanalysis.parser.impl;

import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.vinze.textanalysis.document.DocumentTestHelper;
import fr.vinze.textanalysis.parser.CorpusFactory;
import fr.vinze.textanalysis.parser.DocumentParserFactory;

public class ZipCorpusParserTest {

	static File zipFile;

	@BeforeClass
	public static void initResources() {
		URL urlToFile = DocumentTestHelper.class.getResource("/test.zip");
		zipFile = new File(urlToFile.getPath());
		Assert.assertTrue("input zip file test.zip must exist", zipFile.exists());
	}

	@Before
	public void init() {
		// document parsers
		DocumentParserFactory.clear();
		TxtDocumentParser txtParser = new TxtDocumentParser();
		DocumentParserFactory.registerParser(txtParser);

		// corpus parsers
		CorpusFactory.clear();
		ZIPCorpusParser corpusParser = new ZIPCorpusParser();
		CorpusFactory.registerParser(corpusParser);
	}

	@Test(timeout = 1000)
	public void test() {
		// TODO
		fail("Not yet implemented");
	}

}
