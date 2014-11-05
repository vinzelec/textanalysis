package fr.vinze.textanalysis.lsa.impl;

import java.security.InvalidParameterException;
import java.util.Collection;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.lsa.SemanticSpace;
import fr.vinze.textanalysis.matrix.DocumentTokenMatrix;
import fr.vinze.textanalysis.similarity.VectorSimilarityDistance;
import fr.vinze.textanalysis.svd.SingularValueDecomposition;

public class SemanticSpaceImpl implements SemanticSpace {

	int dimension;
	DocumentTokenMatrix<Double> initalWeightedMatrix;
	SingularValueDecomposition svd;

	public SemanticSpaceImpl(int dimension, DocumentTokenMatrix<Double> initalWeightedMatrix,
			SingularValueDecomposition svd) {
		super();
		if (svd == null) {
			throw new InvalidParameterException("Cannot create a semantic space without a SVD");
		}
		if (svd.getRank() < dimension) {
			throw new InvalidParameterException("The dimension of a semantic space cannot exceed it's SVD rank");
		}
		this.dimension = dimension;
		this.initalWeightedMatrix = initalWeightedMatrix;
		this.svd = svd;
	}

	public int getDimension() {
		return dimension;
	}
	
	public SingularValueDecomposition getsSingularValueDecomposition() {
		return svd;
	}

	public Collection<SegmentedTextDocument> getDocuments() {
		return initalWeightedMatrix.getDocuments();
	}

	public Collection<Token> getTokens() {
		return initalWeightedMatrix.getTokens();
	}

	// util functions

	public double getSimilarity(SegmentedTextDocument doc1, SegmentedTextDocument doc2,
			VectorSimilarityDistance similarity) {
		return similarity.getSimilarityDistance(getDocumentVector(doc1), getDocumentVector(doc2));
	}

	public double getSimilarity(SegmentedTextDocument document, Token token, VectorSimilarityDistance similarity) {
		return similarity.getSimilarityDistance(getDocumentVector(document), getTokenVector(token));
	}

	public double getSimilarity(Token token1, Token token2, VectorSimilarityDistance similarity) {
		return similarity.getSimilarityDistance(getTokenVector(token1), getTokenVector(token2));
	}

	// vector methods

	private double[] getVector(double[] inputVector) {
		double[] resultVector = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			resultVector[i] = inputVector[i] * svd.getS()[i];
		}
		return resultVector;
	}

	public double[] getDocumentVector(SegmentedTextDocument document) {
		int index = initalWeightedMatrix.indexOf(document);
		if (svd.isUDocumentMatrix()) {
			return getVector(svd.getU().getRaw(index));
		} else {
			return getVector(svd.getV().getRaw(index));
		}
	}

	public double[] getTokenVector(Token token) {
		int index = initalWeightedMatrix.indexOf(token);
		if (svd.isUDocumentMatrix()) {
			return getVector(svd.getV().getRaw(index));
		} else {
			return getVector(svd.getU().getRaw(index));
		}
	}

}
