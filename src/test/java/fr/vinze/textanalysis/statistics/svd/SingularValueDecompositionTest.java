package fr.vinze.textanalysis.statistics.svd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.LSAExampleCorpus;
import fr.vinze.textanalysis.statistics.DocumentTokenMatrix;
import fr.vinze.textanalysis.statistics.DocumentTokenMatrixBuilder;
import fr.vinze.textanalysis.statistics.logentropy.LogEntropyMatrixBuilder;
import fr.vinze.textanalysis.statistics.svd.impl.ColtSVDBuilderImpl;

public class SingularValueDecompositionTest {


	DocumentTokenMatrix<Double> inputMatrix;
	SVDBuilder svdBuilder;

	@Before
	public void init() throws Exception {
		SegmentedTextDocumentCorpus corpus = LSAExampleCorpus.getCorpus();
		DocumentTokenMatrixBuilder<? extends DocumentTokenMatrix<Double>> builder = new LogEntropyMatrixBuilder();
		inputMatrix = builder.computeMatrix(corpus);
		svdBuilder = new ColtSVDBuilderImpl();
	}

	@Test(timeout = 1000)
	public void testSVD() throws Exception {
		assertTrue(svdBuilder.accept(inputMatrix));
		SingularValueDecomposition svd = svdBuilder.decompose(inputMatrix);
		assertNotNull(svd);
		assertEquals("the SVD should have a rank of 9 (min of documents*terms)", 9, svd.getRank());
		// SVD can be an approximation, using 0.1 as delta between result and expected
		double[] expectedSV = new double[] { 1.1, 0.96, 0.86, 0.76, 0.66, 0.47, 0.27, 0.17, 0.07 };
		for (int i = 0; i < 9; i++) {
			assertEquals("singular value at i=" + i, expectedSV[i], svd.getS()[i], 0.1);
		}
		// U and V matrix will depend on the terms and document orders that can't be assumed here actually
	}

}
