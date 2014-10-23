package fr.vinze.textanalysis.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.vinze.textanalysis.document.DocumentTestHelper;
import fr.vinze.textanalysis.document.Punctuation;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.mapper.impl.StopWordsFilter;

public class StopWordsFilterTest {

	SegmentedTextMapper stopWordsFilter;
	SegmentedTextDocument document;

	@Before
	public void init() throws Exception {
		URL urlToFile = getClass().getResource("/stop-words-redux_en.txt");
		File swFile = new File(urlToFile.getPath());
		stopWordsFilter = new StopWordsFilter(swFile);
		document = DocumentTestHelper.createTestSegmentedDocument();
	}

	@Test
	public void testSWFilter() throws Exception {
		SegmentedTextDocument output = stopWordsFilter.map(document);
		List<Token> tokens = output.getTokens();
		Token tok = tokens.get(0);
		assertTrue("instanceof on token 0", tok instanceof Word);
		assertEquals("equals on token 0", "This", ((Word) tok).getWord());
		tok = tokens.get(1);
		assertTrue("instanceof on token 1", tok instanceof Word);
		assertEquals("equals on token 1", "first", ((Word) tok).getWord());
		tok = tokens.get(2);
		assertTrue("instanceof on token 2", tok instanceof Word);
		assertEquals("equals on token 2", "line", ((Word) tok).getWord());
		tok = tokens.get(5);
		assertTrue("instanceof on token 5", tok instanceof Punctuation);
	}

}
