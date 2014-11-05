package fr.vinze.textanalysis.matrix.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.CloneFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cern.colt.matrix.impl.SparseObjectMatrix2D;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.utils.ObjectUtils;

/**
 * Classic matrix implementation where values are any extension of {@link Number} based on a
 * {@link SparseObjectMatrix2D}
 * 
 * @author Vinze
 *
 */
public abstract class AbstractColtObjectDocumentTokenMatrix<T extends Number> extends AbstractDocumentTokenMatrix<T> {

	private static final Logger log = LoggerFactory.getLogger(AbstractColtObjectDocumentTokenMatrix.class);

	protected SparseObjectMatrix2D innerMatrix;

	public AbstractColtObjectDocumentTokenMatrix(int initialDocumentSize, int initialTokenSize) {
		super(initialDocumentSize, initialTokenSize);
		innerMatrix = new SparseObjectMatrix2D(initialDocumentSize, initialTokenSize);
	}

	@SuppressWarnings("unchecked")
	public T getValue(SegmentedTextDocument document, Token token) {
		int docId = documentIndex.indexOf(document);
		int tokenId = tokenIndex.indexOf(token);
		if (docId == -1 || tokenId == -1) {
			return null;
		}
		return (T) innerMatrix.get(docId, tokenId);
	}

	public void setValue(SegmentedTextDocument document, Token token, T value) {
		if (value == null) {
			return; // nothing to store
		}
		if (!documentIndex.contains(document)) {
			documentIndex.add(document);
		}
		int docId = documentIndex.indexOf(document);
		if (!tokenIndex.contains(token)) {
			// a copy of the token without metadata
			try {
				tokenIndex.add(ObjectUtils.clone(token));
			} catch (CloneFailedException e) {
				log.warn("failed to clone token " + token + " using original reference", e);
				tokenIndex.add(token);
			} catch (CloneNotSupportedException e) {
				log.warn("failed to clone token " + token + " using original reference", e);
				tokenIndex.add(token);
			}
		}
		int tokenId = tokenIndex.indexOf(token);
		innerMatrix.set(docId, tokenId, value);
	}

	// TODO maybe some data could be stored in an LRU cache to avoid building several time

	public Table<SegmentedTextDocument, Token, T> asTable() {
		Table<SegmentedTextDocument, Token, T> table = HashBasedTable.create();
		for (int i = 0; i < documentIndex.size(); i++) {
			for (int j = 0; j < tokenIndex.size(); j++) {
				@SuppressWarnings("unchecked")
				T count = (T) innerMatrix.get(i, j);
				if (count != null) {
					table.put(documentIndex.get(i), tokenIndex.get(j), count);
				}
			}
		}
		return table;
	}

	public Map<Token, T> getDocumentStatistics(SegmentedTextDocument document) {
		Map<Token, T> stats = new HashMap<Token, T>();
		int docIndex = documentIndex.indexOf(document);
		for (int tokIndex = 0; tokIndex < tokenIndex.size(); tokIndex++) {
			@SuppressWarnings("unchecked")
			T count = (T) innerMatrix.get(docIndex, tokIndex);
			if (count != null) {
				stats.put(tokenIndex.get(tokIndex), count);
			}
		}
		return stats;
	}

	public Map<SegmentedTextDocument, T> getTokenStatistics(Token token) {
		Map<SegmentedTextDocument, T> stats = new HashMap<SegmentedTextDocument, T>();
		int tokIndex = tokenIndex.indexOf(token);
		for (int docIndex = 0; docIndex < documentIndex.size(); docIndex++) {
			@SuppressWarnings("unchecked")
			T count = (T) innerMatrix.get(docIndex, tokIndex);
			if (count != null) {
				stats.put(documentIndex.get(docIndex), count);
			}
		}
		return stats;
	}

}
