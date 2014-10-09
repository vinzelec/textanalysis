package fr.vinze.textanalysis.statistics.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableInt;

import cern.colt.matrix.impl.SparseObjectMatrix2D;

import com.google.common.collect.Table;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.statistics.DocumentTokenMatrix;

/**
 * An implementation of token frequency matrix using Colt library's sparse
 * matrix as inner representation
 * 
 * @author Vinze
 *
 */
public class TokenFrequencyMatrix implements DocumentTokenMatrix<MutableInt> {

	List<SegmentedTextDocument> documentIndex;
	List<Token> tokenIndex;
	SparseObjectMatrix2D innerMatrix;

	public TokenFrequencyMatrix(int initialDocumentSize, int initialTokenSize) {
		innerMatrix = new SparseObjectMatrix2D(initialDocumentSize, initialTokenSize);
		documentIndex = new ArrayList<SegmentedTextDocument>(initialDocumentSize);
		tokenIndex = new ArrayList<Token>(initialTokenSize);
	}

	public MutableInt getValue(SegmentedTextDocument document, Token token) {
		int docId = documentIndex.indexOf(document);
		int tokenId = tokenIndex.indexOf(token);
		return (MutableInt) innerMatrix.get(docId, tokenId);
	}

	public void setValue(SegmentedTextDocument document, Token token, MutableInt value) {
		if (!documentIndex.contains(document)) {
			documentIndex.add(document);
		}
		int docId = documentIndex.indexOf(document);
		if (!tokenIndex.contains(token)) {
			tokenIndex.add(token);
		}
		int tokenId = tokenIndex.indexOf(token);
		innerMatrix.set(docId, tokenId, value);
	}

	public Table<SegmentedTextDocument, Token, MutableInt> asTable() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<Token, MutableInt> getDocumentStatistics(SegmentedTextDocument document) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<SegmentedTextDocument, MutableInt> getTokenStatistics(Token token) {
		// TODO Auto-generated method stub
		return null;
	}

}
