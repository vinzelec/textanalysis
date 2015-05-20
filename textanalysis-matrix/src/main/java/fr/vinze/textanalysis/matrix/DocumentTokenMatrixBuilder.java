package fr.vinze.textanalysis.matrix;

import java.util.Collection;
import java.util.Map;

import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.Token;

/**
 * @param <T>
 *            the type of matrix
 * @param <V>
 *            the type of the matrix values
 */
public interface DocumentTokenMatrixBuilder<V extends Number, T extends DocumentTokenMatrix<V>> {

	T computeMatrix(SegmentedTextDocumentCorpus inputDocuments);

	Map<Token, V> weightQuery(Collection<Token> tokens);
}
