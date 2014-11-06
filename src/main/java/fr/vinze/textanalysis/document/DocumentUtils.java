package fr.vinze.textanalysis.document;

import java.util.Collection;

public abstract class DocumentUtils {

	public static RawTextDocument getRawTextDocument(Collection<RawTextDocument> documents, String documentName) {
		for (RawTextDocument document : documents) {
			if (document.getName().equals(documentName)) {
				return document;
			}
		}
		return null;
	}

	public static SegmentedTextDocument getSegmentedTextDocument(Collection<SegmentedTextDocument> documents,
			String documentName) {
		for (SegmentedTextDocument document : documents) {
			if (document.getName().equals(documentName)) {
				return document;
			}
		}
		return null;
	}

	public static Token getToken(SegmentedTextDocument document, String uniqueID) {
		return getToken(document.getTokens(), uniqueID);
	}

	public static Token getToken(Collection<Token> tokens, String uniqueID) {
		for (Token token : tokens) {
			if (token.getUniqueID().equals(uniqueID)) {
				return token;
			}
		}
		return null;
	}

}
