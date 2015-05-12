package fr.vinze.textanalysis.segmentation;

import org.junit.Before;
import org.junit.Test;

import fr.vinze.textanalysis.document.DocumentTestHelper;
import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.mapper.SegmentedTextMapper;
import fr.vinze.textanalysis.mapper.impl.KeepOnlyWords;
import fr.vinze.textanalysis.segmentation.impl.TextSplitterImpl;
import fr.vinze.textanalysis.segmentation.impl.WordOnlySplitterImpl;

public class WordOnlySplitterTest {

	static final String INPUT = "This is the text that will be tested.";

	Splitter textSplitter, wordSplitter;
	SegmentedTextMapper keeponlywords;
	RawTextDocument inputDocument;

	@Before
	public void init() throws Exception {
		textSplitter = new TextSplitterImpl();
		wordSplitter = new WordOnlySplitterImpl();
		keeponlywords = new KeepOnlyWords();
		inputDocument = new RawTextDocumentImpl("document", INPUT);

	}

	@Test
	public void testWordOnlySplitter() throws Exception {
		SegmentedTextDocument doc1 = wordSplitter.split(inputDocument);
		SegmentedTextDocument doc2 = keeponlywords.map(textSplitter.split(inputDocument));
		DocumentTestHelper.assertDocumentsEquals(doc1, doc2);
	}

}
