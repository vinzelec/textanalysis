package fr.vinze.textanalysis.similarity.impl;

import org.apache.commons.lang3.mutable.MutableDouble;

public class CosineSimilarity extends AbstractVectorSimilarityDistance {

	@Override
	protected double computeDistance(double[] vectorA, double[] vectorB) {
		MutableDouble dotProduct = new MutableDouble();
		MutableDouble aSquareSum = new MutableDouble();
		MutableDouble bSquareSum = new MutableDouble();
		for (int i = 0; i < vectorA.length; i++) {
			dotProduct.add(vectorA[i] * vectorB[i]);
			aSquareSum.add(vectorA[i] * vectorA[i]);
			bSquareSum.add(vectorB[i] * vectorB[i]);
		}
		return dotProduct.doubleValue()
				/ (Math.sqrt(aSquareSum.doubleValue()) * Math.sqrt(bSquareSum.doubleValue()));
	}

}
