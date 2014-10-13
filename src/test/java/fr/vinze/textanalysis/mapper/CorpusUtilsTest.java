package fr.vinze.textanalysis.mapper;

import junit.framework.TestCase;

import org.junit.Test;

import fr.vinze.textanalysis.corpus.CorpusUtils;
import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.mapper.impl.PunctuationCleaner;
import fr.vinze.textanalysis.mapper.impl.ReturnCarriageCleaner;

public class CorpusUtilsTest extends TestCase {

	final static String NAME = "documentName";
	final static String CONTENT = "This «test» \r\nis to be cleant...";

	@Test
	public void testCreateCorpus() {
		RawTextDocument doc1 = new RawTextDocumentImpl("doc1", "content1"), doc2 = new RawTextDocumentImpl("doc2",
				"content2"), doc3 = new RawTextDocumentImpl("doc3", "content3"), doc4 = new RawTextDocumentImpl("doc4",
				"content4"), doc5 = new RawTextDocumentImpl("doc5", "content5"), doc6 = new RawTextDocumentImpl("doc6",
				"content6");
		RawTextDocumentCorpus corpus = CorpusUtils.createCorpus(doc1, doc2, doc3, doc4, doc5, doc6);
		RawTextDocument tempDoc = corpus.getDocument("doc1");
		assertNotNull(tempDoc);
		assertEquals("doc1", tempDoc.getName());
		assertEquals("content1", tempDoc.getContent());
		tempDoc = corpus.getDocument("doc2");
		assertNotNull(tempDoc);
		assertEquals("doc2", tempDoc.getName());
		assertEquals("content2", tempDoc.getContent());
		tempDoc = corpus.getDocument("doc3");
		assertNotNull(tempDoc);
		assertEquals("doc3", tempDoc.getName());
		assertEquals("content3", tempDoc.getContent());
		tempDoc = corpus.getDocument("doc4");
		assertNotNull(tempDoc);
		assertEquals("doc4", tempDoc.getName());
		assertEquals("content4", tempDoc.getContent());
		tempDoc = corpus.getDocument("doc5");
		assertNotNull(tempDoc);
		assertEquals("doc5", tempDoc.getName());
		assertEquals("content5", tempDoc.getContent());
		tempDoc = corpus.getDocument("doc6");
		assertNotNull(tempDoc);
		assertEquals("doc6", tempDoc.getName());
		assertEquals("content6", tempDoc.getContent());
		assertNull(corpus.getDocument("doc7"));
	}

	@Test
	public void testMapDocument() throws Exception {
		testMap(new PunctuationCleaner());
	}

	@Test
	public void testMapAllDocument() throws Exception {
		testMap(new PunctuationCleaner(), new ReturnCarriageCleaner());
	}

	private void testMap(RawTextMapper... mappers) {
		assertTrue(mappers.length > 0);
		RawTextDocument doc11 = new RawTextDocumentImpl(NAME, CONTENT);
		RawTextDocument doc21 = new RawTextDocumentImpl(NAME, CONTENT);
		RawTextDocument doc12 = new RawTextDocumentImpl(NAME + "2", CONTENT);
		RawTextDocument doc22 = new RawTextDocumentImpl(NAME + "2", CONTENT);
		assertEquals("both input documents are equals", doc11.getName(), doc21.getName());
		assertEquals("both input documents are equals", doc11.getContent(), doc21.getContent());
		assertEquals("both input documents are equals", doc12.getName(), doc22.getName());
		assertEquals("both input documents are equals", doc12.getContent(), doc22.getContent());
		// clean then corpus
		for (RawTextMapper mapper : mappers) {
			doc11 = mapper.map(doc11);
			doc12 = mapper.map(doc12);
		}
		RawTextDocumentCorpus corpus1 = CorpusUtils.createCorpus(doc11, doc12);
		// corpus then clean
		RawTextDocumentCorpus corpus2 = CorpusUtils.createCorpus(doc21, doc22);
		if (mappers.length == 1) {
			CorpusUtils.map(corpus2, mappers[0]);
		} else {
			CorpusUtils.mapAll(corpus2, mappers);
		}
		RawTextDocument clean1 = corpus1.getDocument(NAME);
		RawTextDocument clean2 = corpus1.getDocument(NAME);
		assertEquals("both output documents are equals", clean1.getName(), clean2.getName());
		assertEquals("both output documents are equals", clean1.getContent(), clean2.getContent());
		clean1 = corpus1.getDocument(NAME + "2");
		clean2 = corpus1.getDocument(NAME + "2");
		assertEquals("both output documents are equals", clean1.getName(), clean2.getName());
		assertEquals("both output documents are equals", clean1.getContent(), clean2.getContent());
	}

	@Test
	public void testMapSegmented() throws Exception {
		// TODO
	}

	@Test
	public void testMapAllSegmented() throws Exception {
		// TODO
	}

	@Test
	public void testSplit() throws Exception {
		// TODO
	}

}
