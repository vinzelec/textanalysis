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

	// timeout * 10
	@Test(timeout = 1000)
	public void testSVD() throws Exception {
		assertTrue(svdBuilder.accept(inputMatrix));
		SingularValueDecomposition svd = svdBuilder.decompose(inputMatrix);
		assertNotNull(svd);
		assertEquals("the SVD should have a rank of 9 (min of documents*terms)", 9, svd.getRank());
		// TODO test SVD
	}

}
