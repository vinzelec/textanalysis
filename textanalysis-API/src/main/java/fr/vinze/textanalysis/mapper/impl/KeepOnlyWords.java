package fr.vinze.textanalysis.mapper.impl;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.document.impl.SegmentedTextDocumentImpl;
import fr.vinze.textanalysis.mapper.SegmentedTextMapper;

public class KeepOnlyWords implements SegmentedTextMapper {

	public SegmentedTextDocument apply(SegmentedTextDocument document) {
		SegmentedTextDocument newSegmentedDoc = new SegmentedTextDocumentImpl(document.getName(), document.getSource());
		for (Token token : document.getTokens()) {
			if (token instanceof Word) {
				newSegmentedDoc.getTokens().add(token.clone());
			}
		}
		return newSegmentedDoc;
	}

}
