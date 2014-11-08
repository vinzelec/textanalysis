package fr.vinze.textanalysis.lsa;

import static junit.framework.Assert.assertEquals;

import java.security.InvalidParameterException;

import org.junit.Before;
import org.junit.Test;

import fr.vinze.textanalysis.document.DocumentUtils;
import fr.vinze.textanalysis.document.LSAExampleCorpus;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.lsa.impl.SemanticSpaceImpl;
import fr.vinze.textanalysis.matrix.DocumentTokenMatrix;
import fr.vinze.textanalysis.matrix.logentropy.LogEntropyMatrixBuilder;
import fr.vinze.textanalysis.svd.SingularValueDecomposition;
import fr.vinze.textanalysis.svd.impl.ColtSVDBuilderImpl;

public class LSATest {


	DocumentTokenMatrix<Double> inputMatrix;
	SingularValueDecomposition svd;

	@Before
	public void init() throws Exception {
		inputMatrix = (new LogEntropyMatrixBuilder()).computeMatrix(LSAExampleCorpus.getCorpus());
		svd = (new ColtSVDBuilderImpl()).decompose(inputMatrix);
	}

	@Test(expected = InvalidParameterException.class, timeout = 1000)
	public void testOverDimension() throws Exception {
		// cannot create a semantic space with 10 dimension from a SVD with rank = 9
		new SemanticSpaceImpl(10, inputMatrix, svd);
	}

	@Test(timeout = 1000)
	public void testLSA() throws Exception {
		SemanticSpace semspace = new SemanticSpaceImpl(2, inputMatrix, svd);
		SegmentedTextDocument m1 = DocumentUtils.getSegmentedTextDocument(inputMatrix.getDocuments(), "m1");
		SegmentedTextDocument b1 = DocumentUtils.getSegmentedTextDocument(inputMatrix.getDocuments(), "b1");
		Token bread = DocumentUtils.getToken(inputMatrix.getTokens(), "bread");
		Token music = DocumentUtils.getToken(inputMatrix.getTokens(), "music");
		Token roll = DocumentUtils.getToken(inputMatrix.getTokens(), "roll");
		double[] m1expected = new double[] { .07, -.36 };
		double[] b1expected = new double[] { .34, -.35 };
		double[] breadexpected = new double[] { .46, -.09 };
		double[] musicexpected = new double[] { .04, -.34 };
		double[] rollexpected = new double[] { .19, -.34 };
		// using .02 as delta as I may have missed my rounding on manual calculus
		assertExpectedVector(semspace.getDocumentVector(m1), m1expected, .02);
		assertExpectedVector(semspace.getDocumentVector(b1), b1expected, .02);
		assertExpectedVector(semspace.getTokenVector(bread), breadexpected, .02);
		assertExpectedVector(semspace.getTokenVector(music), musicexpected, .02);
		assertExpectedVector(semspace.getTokenVector(roll), rollexpected, .02);
		// TODO test scores according to LSA handbook...
	}

	private void assertExpectedVector(double[] actual, double[] expected, double delta) {
		for (int i = 0; i < actual.length; i++) {
			assertEquals(expected[i], actual[i], delta);
		}
	}

}
