package fr.vinze.textanalysis.similarity;

import junit.framework.Assert;

import org.junit.Test;

import fr.vinze.textanalysis.similarity.impl.CosineSimilarity;
import fr.vinze.textanalysis.similarity.impl.EuclidianDistance;

public class VectorSimilarityDistanceTest {

	@Test
	public void testCosine() throws Exception {
		VectorSimilarityDistance cos = new CosineSimilarity();
		// cos(90) = 0
		Assert.assertEquals(0.0, cos.getSimilarityDistance(new double[] { 0.0, 1.0 }, new double[] { 3.0, 0.0 }));
		// cos (0) = 1
		Assert.assertEquals(1.0, cos.getSimilarityDistance(new double[] { 1.0, 0.0 }, new double[] { 3.3, 0.0 }));
		// cos (45) = v2/2
		Assert.assertEquals(Math.sqrt(2.0) / 2.0,
				cos.getSimilarityDistance(new double[] { 0.0, 3.14 }, new double[] { 2.2, 2.2 }), 0.01);
	}

	@Test
	public void testEuclidianDistance() throws Exception {
		VectorSimilarityDistance eucl = new EuclidianDistance();
		Assert.assertEquals(0.0, eucl.getSimilarityDistance(new double[] { 0.0, 1.0 }, new double[] { 0.0, 1.0 }));
		Assert.assertEquals(2.0, eucl.getSimilarityDistance(new double[] { 0.0, 1.0 }, new double[] { 0.0, 3.0 }), 0.01);
		Assert.assertEquals(Math.sqrt(2.0),
				eucl.getSimilarityDistance(new double[] { 0.0, 1.0 }, new double[] { 1.0, 0.0 }), 0.01);
	}

}
