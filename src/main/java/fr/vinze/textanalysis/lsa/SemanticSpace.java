package fr.vinze.textanalysis.lsa;

import java.util.Collection;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.similarity.VectorSimilarityDistance;
import fr.vinze.textanalysis.svd.SingularValueDecomposition;

/**
 * An LSA semantic space is a model containing a {@link SingularValueDecomposition} linking
 * {@link SegmentedTextDocument}s and {@link Token}s in a vectorial space.
 * 
 * @author Vinze
 *
 */
public interface SemanticSpace {

	SingularValueDecomposition getsSingularValueDecomposition();

	Collection<SegmentedTextDocument> getDocuments();

	Collection<Token> getTokens();

	double[] getDocumentVector(SegmentedTextDocument document);

	double[] getTokenVector(Token token);

	double getSimilarity(SegmentedTextDocument doc1, SegmentedTextDocument doc2, VectorSimilarityDistance similarity);

	double getSimilarity(SegmentedTextDocument document, Token token, VectorSimilarityDistance similarity);

	double getSimilarity(Token token1, Token token2, VectorSimilarityDistance similarity);

}
