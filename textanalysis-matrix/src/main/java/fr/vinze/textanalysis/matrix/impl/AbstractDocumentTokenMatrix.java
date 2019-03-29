package fr.vinze.textanalysis.matrix.impl;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.matrix.DocumentTokenMatrix;

import java.util.*;

public abstract class AbstractDocumentTokenMatrix<T extends Number> implements DocumentTokenMatrix<T> {

	protected List<SegmentedTextDocument> documentIndex;
	protected List<Token> tokenIndex;

	private Map<String, Integer> docindex;
	private int docNextIndex;
	private Map<String, Integer> tokindex;
	private int tokNextIndex;

	public AbstractDocumentTokenMatrix(int initialDocumentSize, int initialTokenSize) {
		super();
		documentIndex = new ArrayList<>(initialDocumentSize);
		tokenIndex = new ArrayList<>(initialTokenSize);
		docindex = new HashMap<>();
		tokindex = new HashMap<>();
		docNextIndex = 0;
		tokNextIndex = 0;
	}

	@Override
	public Collection<SegmentedTextDocument> getDocuments() {
		return documentIndex;
	}

	@Override
	public Collection<Token> getTokens() {
		return tokenIndex;
	}

	@Override
	public int indexOf(SegmentedTextDocument document) {
		Integer index = docindex.get(document.getUniqueID());
		if (index == null) {
			documentIndex.add(document);
			docindex.put(document.getUniqueID(), docNextIndex);
			return docNextIndex++;
		}
		return index;
	}

	@Override
	public int indexOf(Token token) {
		Integer index = tokindex.get(token.getUniqueID());
		if (index == null) {
			tokenIndex.add(token);
			tokindex.put(token.getUniqueID(), tokNextIndex);
			return tokNextIndex++;
		}
		return index;
	}

	protected int getDocumentCount() {
		return docNextIndex;
	}

	protected int getTokenCount() {
		return tokNextIndex;
	}

}
