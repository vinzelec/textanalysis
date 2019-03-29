package fr.vinze.textanalysis.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.mapper.impl.ReturnCarriageCleaner;

public class ReturnCarriageCleanerTest {

	public static final String INPUT = "Line1\nLine2\r\nLine3\r\nLine4\n";

	public static final String EXPECTED = "Line1\nLine2\nLine3\nLine4\n";

	@Test(timeout = 1000)
	public void testReturnCarriageCleaner() throws Exception {
		RawTextDocument doc = new RawTextDocumentImpl("doc", INPUT);
		RawTextMapper cleaner = new ReturnCarriageCleaner();
		RawTextDocument outputDoc = cleaner.apply(doc);
		assertEquals(EXPECTED, outputDoc.getContent());
	}

}
