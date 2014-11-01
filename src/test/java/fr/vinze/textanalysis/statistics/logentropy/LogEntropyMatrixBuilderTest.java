package fr.vinze.textanalysis.statistics.logentropy;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.LSAExampleCorpus;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.document.impl.WordImpl;
import fr.vinze.textanalysis.statistics.DocumentTokenMatrix;
import fr.vinze.textanalysis.statistics.DocumentTokenMatrixBuilder;
import fr.vinze.textanalysis.statistics.impl.ColtDoubleDocumentTokenMatrix;

public class LogEntropyMatrixBuilderTest {

	SegmentedTextDocumentCorpus corpus;
	DocumentTokenMatrixBuilder<ColtDoubleDocumentTokenMatrix> matrixBuilder;

	@Before
	public void init() throws Exception {
		matrixBuilder = new LogEntropyMatrixBuilder();
		corpus = LSAExampleCorpus.getCorpus();
	}

	@Test(timeout = 1000)
	public void testLogEntropyMatrixBuilder() throws Exception {
		DocumentTokenMatrix<Double> matrix = matrixBuilder.computeMatrix(corpus);
		// some words and documents to test
		Word bread = new WordImpl("bread");
		Word music = new WordImpl("music");
		Word roll  = new WordImpl("roll");
		SegmentedTextDocument m1 = corpus.getDocument("m1");
		SegmentedTextDocument b1 = corpus.getDocument("b1");

		assertNotNullAndEquals(0.0, matrix.getValue(m1, bread), 0.0);
		assertNotNullAndEquals(0.347, matrix.getValue(m1, music), 0.001);
		assertNotNullAndEquals(0.256, matrix.getValue(m1, roll), 0.001);
		assertNotNullAndEquals(0.474, matrix.getValue(b1, bread), 0.001);
		assertNotNullAndEquals(0.0, matrix.getValue(b1, music), 0.0);
		assertNotNullAndEquals(0.256, matrix.getValue(b1, roll), 0.001);
	}

	// assert equals avoiding null pointer if no value returned
	private void assertNotNullAndEquals(double expected, Double actual, double delta) {
		assertNotNull(actual);
		assertEquals(expected, actual, delta);
	}

}
