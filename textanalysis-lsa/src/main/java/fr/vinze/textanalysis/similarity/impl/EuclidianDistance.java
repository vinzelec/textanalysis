package fr.vinze.textanalysis.similarity.impl;

import org.apache.commons.lang3.mutable.MutableDouble;


public class EuclidianDistance extends AbstractVectorSimilarityDistance {

	@Override
	protected double computeDistance(double[] vector1, double[] vector2) {
		MutableDouble sum = new MutableDouble();
		for (int i = 0; i < vector1.length; i++) {
			double diff = vector1[i] - vector2[i];
			sum.add(diff * diff);
		}
		return Math.sqrt(sum.doubleValue());
	}

}
