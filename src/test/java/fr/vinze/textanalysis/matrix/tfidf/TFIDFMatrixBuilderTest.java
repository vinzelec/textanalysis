package fr.vinze.textanalysis.matrix.tfidf;

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
 * example source : http://en.wikipedia.org/wiki/Tf%E2%80%93idf
 * 
 * @author Vinze
 *
 */
public class TFIDFMatrixBuilderTest {

	// according to example word count at http://en.wikipedia.org/wiki/Tf%E2%80%93idf#Example_of_tf.E2.80.93idf
	String doc1content = "this is a a sample";
	String doc2content = "this is another another example example example";

	protected SegmentedTextDocumentCorpus corpus;
	protected TFIDFMatrixBuilder matrixBuilder;

	@Before
	public void init() throws Exception {
		matrixBuilder = new TFIDFMatrixBuilder();
		RawTextDocumentCorpus rawCorpus = CorpusUtils.createCorpus(new RawTextDocumentImpl("doc1", doc1content),
				new RawTextDocumentImpl("doc2", doc2content));
		// split and keep only words
		corpus = CorpusUtils.mapAll(CorpusUtils.split(rawCorpus, new TextSplitterImpl()), new KeepOnlyWords(),
				new TokenCounter());
	}

	@Test(timeout = 1000)
	public void testMatrixBuilder() throws Exception {
		DocumentTokenMatrix<Double> result = matrixBuilder.computeMatrix(corpus);
		assertNotNull(result);
		assertEquals("corpus contains 2 documents", 2, result.getDocuments().size());
		assertEquals("corpus contains 6 words", 6, result.getTokens().size());
		// search for doc2
		SegmentedTextDocument doc2 = null;
		for (SegmentedTextDocument doc : result.getDocuments()) {
			if ("doc2".equals(doc.getName())) {
				doc2 = doc;
			}
		}
		Double score = result.getValue(doc2, new WordImpl("this"));
		assertNotNull(score);
		assertEquals("tf-idf of 'this' for document 2 is 0", 0.0, score);
		score = result.getValue(doc2, new WordImpl("example"));
		assertNotNull(score);
		assertEquals("tf-idf of 'example' for document 2 is ~0.903", 0.903, score, 0.001);
	}
}
