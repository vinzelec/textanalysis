package fr.vinze.textanalysis.statistics;

import fr.vinze.textanalysis.document.SegmentedTextDocument;

public interface DocumentTokenMatrixBuilder<T extends DocumentTokenMatrix<?>> {

	T computeMatrix(SegmentedTextDocument inputDocument);

}
