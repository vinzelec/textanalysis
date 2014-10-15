package fr.vinze.textanalysis.statistics.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableInt;

import cern.colt.matrix.impl.SparseObjectMatrix2D;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.statistics.DocumentTokenMatrix;

/**
 * <p>
 * An implementation of token frequency matrix using Colt library's sparse matrix as inner representation.
 * </p>
 * <p>
 * When the value is <code>null</code> it won't be stored... <code>null</code> for
 * {@link #getValue(SegmentedTextDocument, Token)} means the value is same as a MutableInt which
 * {@link MutableInt#getValue()} would return <code>0</code>.
 * </p>
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
		if (value == null) {
			return; // nothing to store
		}
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

	// TODO maybe some data could be stored in an LRU cache to avoid building several time

	public Table<SegmentedTextDocument, Token, MutableInt> asTable() {
		Table<SegmentedTextDocument, Token, MutableInt> table = HashBasedTable.create();
		for (int i = 0; i < documentIndex.size(); i++) {
			for (int j = 0; j < tokenIndex.size(); j++) {
				MutableInt count = (MutableInt) innerMatrix.get(i, j);
				if (count != null) {
					table.put(documentIndex.get(i), tokenIndex.get(j), count);
				}
			}
		}
		return table;
	}

	public Map<Token, MutableInt> getDocumentStatistics(SegmentedTextDocument document) {
		Map<Token, MutableInt> stats = new HashMap<Token, MutableInt>();
		int docIndex = documentIndex.indexOf(document);
		for (int tokIndex = 0; tokIndex < tokenIndex.size(); tokIndex++) {
			MutableInt count = (MutableInt) innerMatrix.get(docIndex, tokIndex);
			if (count != null) {
				stats.put(tokenIndex.get(tokIndex), count);
			}
		}
		return stats;
	}

	public Map<SegmentedTextDocument, MutableInt> getTokenStatistics(Token token) {
		Map<SegmentedTextDocument, MutableInt> stats = new HashMap<SegmentedTextDocument, MutableInt>();
		int tokIndex = tokenIndex.indexOf(token);
		for (int docIndex = 0; docIndex < documentIndex.size(); docIndex++) {
			MutableInt count = (MutableInt) innerMatrix.get(docIndex, tokIndex);
			if (count != null) {
				stats.put(documentIndex.get(tokIndex), count);
			}
		}
		return stats;
	}

}
