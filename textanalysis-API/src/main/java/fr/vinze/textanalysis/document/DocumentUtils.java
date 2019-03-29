package fr.vinze.textanalysis.document;

import java.util.Collection;

public interface DocumentUtils {

	static RawTextDocument getRawTextDocument(Collection<RawTextDocument> documents, String documentName) {
		for (RawTextDocument document : documents) {
			if (document.getName().equals(documentName)) {
				return document;
			}
		}
		return null;
	}

	static SegmentedTextDocument getSegmentedTextDocument(Collection<SegmentedTextDocument> documents,
														  String documentName) {
		for (SegmentedTextDocument document : documents) {
			if (document.getName().equals(documentName)) {
				return document;
			}
		}
		return null;
	}

	static Token getToken(SegmentedTextDocument document, String uniqueID) {
		return getToken(document.getTokens(), uniqueID);
	}

	static Token getToken(Collection<Token> tokens, String uniqueID) {
		for (Token token : tokens) {
			if (token.getUniqueID().equals(uniqueID)) {
				return token;
			}
		}
		return null;
	}

}
