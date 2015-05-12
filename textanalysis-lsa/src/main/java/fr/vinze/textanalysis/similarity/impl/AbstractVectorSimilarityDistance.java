package fr.vinze.textanalysis.similarity.impl;

import java.security.InvalidParameterException;

import fr.vinze.textanalysis.similarity.VectorSimilarityDistance;

/**
 * Abstract implementation that perform the generic check that input parameters are ok
 * 
 * @author Vinze
 *
 */
public abstract class AbstractVectorSimilarityDistance implements VectorSimilarityDistance {

	public double getSimilarityDistance(double[] vector1, double[] vector2) {
		if (vector1 == null || vector2 == null) {
			throw new InvalidParameterException("A similarity distance can't be computed on a null vector");
		}
		if (vector1.length != vector2.length) {
			throw new InvalidParameterException(
					"A similarity distance can't be computed on two vectors of different dimension");
		}

		if (vector1.length == 0) {
			throw new InvalidParameterException(
					"A similarity distance can't be computed on two vectors without any dimension");
		}
		return computeDistance(vector1, vector2);
	}

	protected abstract double computeDistance(double[] vector1, double[] vector2);

}
