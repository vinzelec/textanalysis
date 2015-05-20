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
import fr.vinze.textanalysis.matrix.DocumentTokenMatrixBuilder;
import fr.vinze.textanalysis.matrix.logentropy.LogEntropyMatrixBuilder;
import fr.vinze.textanalysis.similarity.VectorSimilarityDistance;
import fr.vinze.textanalysis.similarity.impl.CosineSimilarity;
import fr.vinze.textanalysis.svd.SingularValueDecomposition;
import fr.vinze.textanalysis.svd.impl.ColtSVDBuilderImpl;

public class LSATest {


	DocumentTokenMatrix<Double> inputMatrix;
	SingularValueDecomposition svd;
	DocumentTokenMatrixBuilder<Double, ? extends DocumentTokenMatrix<Double>> builder;
	SemanticSpace semspace;
	SegmentedTextDocument m1, b1;
	Token bread, music, roll, recipe;

	@Before
	public void init() throws Exception {
		builder = new LogEntropyMatrixBuilder();
		inputMatrix = builder.computeMatrix(LSAExampleCorpus.getCorpus());
		svd = (new ColtSVDBuilderImpl()).decompose(inputMatrix);
		semspace = new SemanticSpaceImpl(2, inputMatrix, svd, builder);
		m1 = DocumentUtils.getSegmentedTextDocument(inputMatrix.getDocuments(), "m1");
		b1 = DocumentUtils.getSegmentedTextDocument(inputMatrix.getDocuments(), "b1");
		bread = DocumentUtils.getToken(inputMatrix.getTokens(), "bread");
		music = DocumentUtils.getToken(inputMatrix.getTokens(), "music");
		roll = DocumentUtils.getToken(inputMatrix.getTokens(), "roll");
		recipe = DocumentUtils.getToken(inputMatrix.getTokens(), "recipe");
	}

	@Test(expected = InvalidParameterException.class, timeout = 1000)
	public void testOverDimension() throws Exception {
		// cannot create a semantic space with 10 dimension from a SVD with rank = 9
		new SemanticSpaceImpl(10, inputMatrix, svd, builder);
	}

	@Test(timeout = 1000)
	public void testLSA() throws Exception {
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
	}

	@Test(timeout = 1000)
	public void testQuery() throws Exception {
		VectorSimilarityDistance cos = new CosineSimilarity();
		// LSA handbook test query is "Recipe for White Bread"... containing two tokens from semantic space
		double[] query = semspace.getQueryVector(bread, recipe);
		assertEquals(.842, cos.getSimilarityDistance(query, semspace.getDocumentVector(b1)), .001);
		// TODO test scores according to LSA handbook...

	}

	private void assertExpectedVector(double[] actual, double[] expected, double delta) {
		for (int i = 0; i < actual.length; i++) {
			assertEquals(expected[i], actual[i], delta);
		}
	}

}
