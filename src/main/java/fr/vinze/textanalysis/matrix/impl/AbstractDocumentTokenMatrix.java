package fr.vinze.textanalysis.matrix.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.matrix.DocumentTokenMatrix;

public abstract class AbstractDocumentTokenMatrix<T extends Number> implements DocumentTokenMatrix<T> {

	protected List<SegmentedTextDocument> documentIndex;
	protected List<Token> tokenIndex;

	public AbstractDocumentTokenMatrix(int initialDocumentSize, int initialTokenSize) {
		super();
		documentIndex = new ArrayList<SegmentedTextDocument>(initialDocumentSize);
		tokenIndex = new ArrayList<Token>(initialTokenSize);
	}

	public Collection<SegmentedTextDocument> getDocuments() {
		return documentIndex;
	}

	public Collection<Token> getTokens() {
		return tokenIndex;
	}

}
