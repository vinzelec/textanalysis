package fr.vinze.textanalysis.matrix;

import java.util.Collection;
import java.util.Map;

import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.Token;

public interface DocumentTokenMatrixBuilder<V, T extends DocumentTokenMatrix<? extends V>> {

	T computeMatrix(SegmentedTextDocumentCorpus inputDocuments);

	Map<Token, V> weightQuery(Collection<Token> tokens);
}
