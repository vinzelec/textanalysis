package fr.vinze.textanalysis.corpus;

import fr.vinze.textanalysis.document.DocumentTestHelper;
import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.mapper.RawTextMapper;
import fr.vinze.textanalysis.mapper.SegmentedTextMapper;
import fr.vinze.textanalysis.mapper.impl.KeepOnlyWords;
import fr.vinze.textanalysis.mapper.impl.PunctuationCleaner;
import fr.vinze.textanalysis.mapper.impl.ReturnCarriageCleaner;
import fr.vinze.textanalysis.mapper.impl.SegmentedToLowercase;
import fr.vinze.textanalysis.segmentation.Splitter;
import fr.vinze.textanalysis.segmentation.impl.TextSplitterImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CorpusUtilsTest {

	@Test(timeout = 1000)
	public void testCreateCorpus() {
		RawTextDocument doc1 = new RawTextDocumentImpl("doc1", "content1"), doc2 = new RawTextDocumentImpl("doc2",
				"content2"), doc3 = new RawTextDocumentImpl("doc3", "content3"), doc4 = new RawTextDocumentImpl("doc4",
				"content4"), doc5 = new RawTextDocumentImpl("doc5", "content5"), doc6 = new RawTextDocumentImpl("doc6",
				"content6");
		RawTextDocumentCorpus corpus = CorpusUtils.createCorpus(doc1, doc2, doc3, doc4, doc5, doc6);
		testRawDocumentContent(corpus.getDocument("doc1"), "doc1", "content1");
		testRawDocumentContent(corpus.getDocument("doc2"), "doc2", "content2");
		testRawDocumentContent(corpus.getDocument("doc3"), "doc3", "content3");
		testRawDocumentContent(corpus.getDocument("doc4"), "doc4", "content4");
		testRawDocumentContent(corpus.getDocument("doc5"), "doc5", "content5");
		testRawDocumentContent(corpus.getDocument("doc6"), "doc6", "content6");
		assertNull(corpus.getDocument("doc7"));
	}

	private void testRawDocumentContent(RawTextDocument doc, String expectedName, String expectedContent) {
		assertNotNull(doc);
		assertEquals(expectedName, doc.getName());
		assertEquals(expectedContent, doc.getContent());
	}

	final static String NAME = "documentName";
	final static String CONTENT = "This «test» \r\nis to be cleant...";

	RawTextDocument rawdoc11, rawdoc21, rawdoc12, rawdoc22;

	@Before
	public void init() {
		// not used by testCreateCorpus but all others...
		rawdoc11 = new RawTextDocumentImpl(NAME, CONTENT);
		rawdoc21 = new RawTextDocumentImpl(NAME, CONTENT);
		rawdoc12 = new RawTextDocumentImpl(NAME + "2", CONTENT);
		rawdoc22 = new RawTextDocumentImpl(NAME + "2", CONTENT);
		DocumentTestHelper.assertDocumentsEquals(rawdoc11, rawdoc21);
		DocumentTestHelper.assertDocumentsEquals(rawdoc12, rawdoc22);

	}

	@Test(timeout = 1000)
	public void testMapDocument() throws Exception {
		testMap(new PunctuationCleaner());
	}

	@Test(timeout = 1000)
	public void testMapAllDocument() throws Exception {
		testMap(new PunctuationCleaner(), new ReturnCarriageCleaner());
	}

	private void testMap(RawTextMapper... mappers) {
		assertTrue(mappers.length > 0);
		// apply then corpus
		for (RawTextMapper mapper : mappers) {
			rawdoc11 = mapper.apply(rawdoc11);
			rawdoc12 = mapper.apply(rawdoc12);
		}
		RawTextDocumentCorpus corpus1 = CorpusUtils.createCorpus(rawdoc11, rawdoc12);
		// corpus then apply
		RawTextDocumentCorpus corpus2 = CorpusUtils.createCorpus(rawdoc21, rawdoc22);
		if (mappers.length == 1) {
			corpus2 = CorpusUtils.map(corpus2, mappers[0]);
		} else {
			corpus2 = CorpusUtils.mapAll(corpus2, mappers);
		}
		// test same result
		DocumentTestHelper.assertDocumentsEquals(corpus1.getDocument(NAME), corpus2.getDocument(NAME));
		DocumentTestHelper.assertDocumentsEquals(corpus1.getDocument(NAME + "2"), corpus2.getDocument(NAME + "2"));
	}

	@Test(timeout = 1000)
	public void testMapSegmented() throws Exception {
		testMap(new KeepOnlyWords());
	}

	@Test(timeout = 1000)
	public void testMapAllSegmented() throws Exception {
		testMap(new KeepOnlyWords(), new SegmentedToLowercase());
	}

	private void testMap(SegmentedTextMapper... mappers) {
		assertTrue(mappers.length > 0);
		// segment
		Splitter splitter = new TextSplitterImpl();
		SegmentedTextDocument doc11 = splitter.apply(rawdoc11);
		SegmentedTextDocument doc21 = splitter.apply(rawdoc21);
		SegmentedTextDocument doc12 = splitter.apply(rawdoc12);
		SegmentedTextDocument doc22 = splitter.apply(rawdoc22);
		DocumentTestHelper.assertDocumentsEquals(doc11, doc21);
		DocumentTestHelper.assertDocumentsEquals(doc12, doc22);
		// apply then create a corpus
		for (SegmentedTextMapper mapper : mappers) {
			doc11 = mapper.apply(doc11);
			doc12 = mapper.apply(doc12);
		}
		SegmentedTextDocumentCorpus corpus1 = CorpusUtils.createCorpus(doc11, doc12);
		// create a corpus then apply
		SegmentedTextDocumentCorpus corpus2 = CorpusUtils.createCorpus(doc21, doc22);
		if (mappers.length == 1) {
			corpus2 = CorpusUtils.map(corpus2, mappers[0]);
		} else {
			corpus2 = CorpusUtils.mapAll(corpus2, mappers);
		}
		// test same result
		DocumentTestHelper.assertDocumentsEquals(corpus1.getDocument(NAME), corpus2.getDocument(NAME));
		DocumentTestHelper.assertDocumentsEquals(corpus1.getDocument(NAME + "2"),
				corpus2.getDocument(NAME + "2"));
	}

	@Test(timeout = 1000)
	public void testSplit() throws Exception {
		Splitter splitter = new TextSplitterImpl();
		// corpus then split
		RawTextDocumentCorpus corpusIn = CorpusUtils.createCorpus(rawdoc11, rawdoc12);
		SegmentedTextDocumentCorpus corpus1 = CorpusUtils.split(corpusIn, splitter);
		// split then corpus
		SegmentedTextDocument doc21 = splitter.apply(rawdoc21);
		SegmentedTextDocument doc22 = splitter.apply(rawdoc22);
		SegmentedTextDocumentCorpus corpus2 = CorpusUtils.createCorpus(doc21, doc22);
		// test same result
		DocumentTestHelper.assertDocumentsEquals(corpus1.getDocument(NAME), corpus2.getDocument(NAME));
		DocumentTestHelper.assertDocumentsEquals(corpus1.getDocument(NAME + "2"),
				corpus2.getDocument(NAME + "2"));
	}

}
