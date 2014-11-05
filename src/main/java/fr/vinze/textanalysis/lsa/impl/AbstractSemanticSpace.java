package fr.vinze.textanalysis.lsa.impl;

import java.util.Collection;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.lsa.SemanticSpace;
import fr.vinze.textanalysis.similarity.VectorSimilarityDistance;
import fr.vinze.textanalysis.svd.SingularValueDecomposition;

public abstract class AbstractSemanticSpace implements SemanticSpace {

	public SingularValueDecomposition getsSingularValueDecomposition() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<SegmentedTextDocument> getDocuments() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Token> getTokens() {
		// TODO Auto-generated method stub
		return null;
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

}
