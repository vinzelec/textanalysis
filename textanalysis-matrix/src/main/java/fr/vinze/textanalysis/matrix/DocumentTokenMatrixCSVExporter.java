package fr.vinze.textanalysis.matrix;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DocumentTokenMatrixCSVExporter implements Function<DocumentTokenMatrix<? extends Number>, String> {

	@Override
	public String apply(DocumentTokenMatrix<? extends Number> matrix) {
		StringBuilder builder = new StringBuilder();
		// must retain the order
		List<SegmentedTextDocument> orderedDocument = new ArrayList<>();
		for (SegmentedTextDocument document : matrix.getDocuments()) {
			builder.append(";").append(document.getName());
			orderedDocument.add(document);
		}
		builder.append("\n");
		for (Token token : matrix.getTokens()) {
			builder.append(token.getUniqueID());
			for (SegmentedTextDocument segmentedTextDocument : orderedDocument) {
				builder.append(";").append(matrix.getValue(segmentedTextDocument, token));
			}
			builder.append("\n");
		}
		// IMPROVE maybe remove last return carriage
		return builder.toString();
	}

}
