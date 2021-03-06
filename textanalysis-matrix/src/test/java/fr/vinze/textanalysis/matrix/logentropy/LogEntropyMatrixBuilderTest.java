package fr.vinze.textanalysis.matrix.logentropy;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.DocumentUtils;
import fr.vinze.textanalysis.document.ExampleCorpus;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.matrix.DocumentTokenMatrix;
import fr.vinze.textanalysis.matrix.DocumentTokenMatrixBuilder;
import fr.vinze.textanalysis.matrix.impl.ColtDoubleDocumentTokenMatrix;

public class LogEntropyMatrixBuilderTest {

	SegmentedTextDocumentCorpus corpus;
	DocumentTokenMatrixBuilder<Double, ColtDoubleDocumentTokenMatrix> matrixBuilder;

	@Before
	public void init() throws Exception {
		matrixBuilder = new LogEntropyMatrixBuilder();
		corpus = ExampleCorpus.getCorpus();
	}

	public DocumentTokenMatrix<Double> buildMatrix() {
		return matrixBuilder.computeMatrix(corpus);
	}

	@Test(timeout = 1000)
	public void testLogEntropyMatrixBuilder() throws Exception {
		DocumentTokenMatrix<Double> matrix = buildMatrix();
		// some words and documents to test
		Token bread = DocumentUtils.getToken(matrix.getTokens(), "bread");
		Token music = DocumentUtils.getToken(matrix.getTokens(), "music");
		Token roll = DocumentUtils.getToken(matrix.getTokens(), "roll");
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
