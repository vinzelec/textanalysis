package fr.vinze.textanalysis.statistics.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.statistics.DocumentTokenMatrix;

public abstract class AbstractMatrix<T extends Number> implements DocumentTokenMatrix<T> {

	protected List<SegmentedTextDocument> documentIndex;
	protected List<Token> tokenIndex;

	public AbstractMatrix(int initialDocumentSize, int initialTokenSize) {
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
