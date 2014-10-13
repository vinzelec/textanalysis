package fr.vinze.textanalysis.statistics;

import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;

public interface DocumentTokenMatrixBuilder<T extends DocumentTokenMatrix<?>> {

	T computeMatrix(SegmentedTextDocumentCorpus inputDocuments);

}
