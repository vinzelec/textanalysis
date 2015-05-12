package fr.vinze.textanalysis.matrix.impl;

import java.util.HashMap;
import java.util.Map;

import cern.colt.matrix.impl.SparseDoubleMatrix2D;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;

/**
 * Classic matrix implementation where values are {@link Double} based on a {@link SparseDoubleMatrix2D}
 * 
 * @author Vinze
 *
 */
public class ColtDoubleDocumentTokenMatrix extends AbstractDocumentTokenMatrix<Double> {

	protected SparseDoubleMatrix2D innerMatrix;

	final boolean documentsAreRows;

	public ColtDoubleDocumentTokenMatrix(int initialDocumentSize, int initialTokenSize) {
		super(initialDocumentSize, initialTokenSize);
		documentsAreRows = initialDocumentSize >= initialTokenSize;
		if (documentsAreRows) {
			innerMatrix = new SparseDoubleMatrix2D(initialDocumentSize, initialTokenSize);
		} else {
			innerMatrix = new SparseDoubleMatrix2D(initialTokenSize, initialDocumentSize);
		}
	}

	public Double getValue(SegmentedTextDocument document, Token token) {
		int docId = indexOf(document);
		int tokenId = indexOf(token);
		if (docId == -1 || tokenId == -1) {
			return null;
		}
		if (documentsAreRows) {
			return innerMatrix.get(docId, tokenId);
		} else {
			return innerMatrix.get(tokenId, docId);
		}
	}

	public void setValue(SegmentedTextDocument document, Token token, Double value) {
		if (value == null) {
			return; // nothing to store
		}
		int docId = indexOf(document);
		int tokenId = indexOf(token);

		if (documentsAreRows) {
			innerMatrix.set(docId, tokenId, value);
		} else {
			innerMatrix.set(tokenId, docId, value);
		}
	}

	// IMPROVE maybe some data could be stored in an LRU cache to avoid building several time

	public Table<SegmentedTextDocument, Token, Double> asTable() {
		Table<SegmentedTextDocument, Token, Double> table = HashBasedTable.create();
		for (int i = 0; i < getDocumentCount(); i++) {
			for (int j = 0; j < tokenIndex.size(); j++) {
				double count = documentsAreRows ? innerMatrix.get(i, j) : innerMatrix.get(j, i);
				if (count != 0) {
					table.put(documentIndex.get(i), tokenIndex.get(j), count);
				}
			}
		}
		return table;
	}

	public Map<Token, Double> getDocumentStatistics(SegmentedTextDocument document) {
		Map<Token, Double> stats = new HashMap<Token, Double>();
		int docIndex = indexOf(document);
		for (int tokIndex = 0; tokIndex < tokenIndex.size(); tokIndex++) {
			double count = (Double) (documentsAreRows ? innerMatrix.get(docIndex, tokIndex) : innerMatrix.get(tokIndex,
					docIndex));
			if (count != 0) {
				stats.put(tokenIndex.get(tokIndex), count);
			}
		}
		return stats;
	}

	public Map<SegmentedTextDocument, Double> getTokenStatistics(Token token) {
		Map<SegmentedTextDocument, Double> stats = new HashMap<SegmentedTextDocument, Double>();
		int tokIndex = indexOf(token);
		for (int docIndex = 0; docIndex < documentIndex.size(); docIndex++) {
			double count = documentsAreRows ? innerMatrix.get(docIndex, tokIndex) : innerMatrix.get(tokIndex, docIndex);
			if (count != 0) {
				stats.put(documentIndex.get(docIndex), count);
			}
		}
		return stats;
	}

	public SparseDoubleMatrix2D getInnerMatrix() {
		return innerMatrix;
	}

	/**
	 * in any case within {@link #getInnerMatrix()} #rows >= #columns.
	 * This flag indicates if inner matrix use documents as rows instead of column.
	 * 
	 * @return
	 */
	public boolean areDocumentsRows() {
		return documentsAreRows;
	}

}
