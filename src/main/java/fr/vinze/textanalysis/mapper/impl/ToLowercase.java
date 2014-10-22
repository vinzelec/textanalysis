package fr.vinze.textanalysis.mapper.impl;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.document.impl.SegmentedTextDocumentImpl;
import fr.vinze.textanalysis.document.impl.WordImpl;
import fr.vinze.textanalysis.mapper.RawTextMapper;
import fr.vinze.textanalysis.mapper.SegmentedTextMapper;

public class ToLowercase implements SegmentedTextMapper, RawTextMapper {

	public SegmentedTextDocument map(SegmentedTextDocument document) {
		SegmentedTextDocument newSegmentedDoc = new SegmentedTextDocumentImpl(document.getName(), document.getSource());
		for (Token token : document.getTokens()) {
			Token newToken;
			if (token instanceof Word) {
				newToken = new WordImpl(((Word) token).getWord().toLowerCase());
				newToken.mergeMetadata(token);
			} else {
				newToken = token.clone();
			}

			newSegmentedDoc.getTokens().add(newToken);
		}
		return newSegmentedDoc;
	}

	public RawTextDocument map(RawTextDocument document) {
		return new RawTextDocumentImpl(document.getName(), document.getContent().toLowerCase(), document.getRawSource());
	}

}
