package fr.vinze.textanalysis.resources;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Collection;

import org.junit.Test;

public class StopWordsTest {

	@Test(timeout = 1000)
	public void testStopWordsResources() throws Exception {
		Collection<File> files = StopWords.getInstance().getStopWords("en");
		assertEquals("6 stop words files for english", 6, files.size());
		files = StopWords.getInstance().getStopWords("fr");
		assertEquals("2 stop words files for french", 2, files.size());
	}

}
