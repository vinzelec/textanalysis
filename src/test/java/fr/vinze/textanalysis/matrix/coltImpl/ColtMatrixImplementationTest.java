package fr.vinze.textanalysis.matrix.coltImpl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import fr.vinze.textanalysis.corpus.CorpusUtils;
import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.document.impl.WordImpl;
import fr.vinze.textanalysis.mapper.impl.KeepOnlyWords;
import fr.vinze.textanalysis.mapper.impl.TokenCounter;
import fr.vinze.textanalysis.matrix.DocumentTokenMatrix;
import fr.vinze.textanalysis.matrix.tfidf.TFIDFMatrixBuilder;
import fr.vinze.textanalysis.segmentation.impl.TextSplitterImpl;

/**
 * Perform the same as TFIDFMatrixBuilderTest but with more documents than token
 * 
 * @author Vinze
 *
 */
public class ColtMatrixImplementationTest {

	// change init so there is more document than token
	String doc1Content = "a a a";
	String doc2Content = "a b b";
	String doc3Content = "a a b";

	protected SegmentedTextDocumentCorpus corpus;
	protected TFIDFMatrixBuilder matrixBuilder;

	@Before
	public void init() throws Exception {
		matrixBuilder = new TFIDFMatrixBuilder();
		RawTextDocumentCorpus rawCorpus = CorpusUtils.createCorpus(new RawTextDocumentImpl("doc1", doc1Content),
				new RawTextDocumentImpl("doc2", doc2Content), new RawTextDocumentImpl("doc3", doc3Content));
		// split and keep only words
		corpus = CorpusUtils.mapAll(CorpusUtils.split(rawCorpus, new TextSplitterImpl()), new KeepOnlyWords(),
				new TokenCounter());
	}

	@Test(timeout = 1000)
	public void testMatrixBuilder() throws Exception {
		DocumentTokenMatrix<Double> result = matrixBuilder.computeMatrix(corpus);
		assertNotNull(result);
		assertEquals("corpus contains 3 documents", 3, result.getDocuments().size());
		assertEquals("corpus contains 2 words", 2, result.getTokens().size());
		// search for doc2
		SegmentedTextDocument doc2 = null;
		for (SegmentedTextDocument doc : result.getDocuments()) {
			if ("doc2".equals(doc.getName())) {
				doc2 = doc;
			}
		}
		Double score = result.getValue(doc2, new WordImpl("a"));
		assertNotNull(score);
		assertEquals("tf-idf of 'a' for document 2 is 0", 0.0, score);
		score = result.getValue(doc2, new WordImpl("b"));
		assertNotNull(score);
		assertEquals("tf-idf of 'b' for document 2 is ~0.352", 0.352, score, 0.001);
	}
}
