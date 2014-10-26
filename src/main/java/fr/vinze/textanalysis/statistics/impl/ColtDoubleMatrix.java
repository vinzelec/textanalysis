package fr.vinze.textanalysis.statistics.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.CloneFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cern.colt.matrix.impl.SparseDoubleMatrix2D;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.utils.ObjectUtils;

/**
 * Classic matrix implementation where values are {@link Double} based on a {@link SparseDoubleMatrix2D}
 * 
 * @author Vinze
 *
 */
public class ColtDoubleMatrix extends AbstractMatrix<Double> {

	private static final Logger log = LoggerFactory.getLogger(ColtDoubleMatrix.class);

	protected SparseDoubleMatrix2D innerMatrix;

	public ColtDoubleMatrix(int initialDocumentSize, int initialTokenSize) {
		super(initialDocumentSize, initialTokenSize);
		innerMatrix = new SparseDoubleMatrix2D(initialDocumentSize, initialTokenSize);
	}

	public Double getValue(SegmentedTextDocument document, Token token) {
		int docId = documentIndex.indexOf(document);
		int tokenId = tokenIndex.indexOf(token);
		if (docId == -1 || tokenId == -1) {
			return null;
		}
		return innerMatrix.get(docId, tokenId);
	}

	public void setValue(SegmentedTextDocument document, Token token, Double value) {
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

	public Table<SegmentedTextDocument, Token, Double> asTable() {
		Table<SegmentedTextDocument, Token, Double> table = HashBasedTable.create();
		for (int i = 0; i < documentIndex.size(); i++) {
			for (int j = 0; j < tokenIndex.size(); j++) {
				double count = innerMatrix.get(i, j);
				if (count != 0) {
					table.put(documentIndex.get(i), tokenIndex.get(j), count);
				}
			}
		}
		return table;
	}

	public Map<Token, Double> getDocumentStatistics(SegmentedTextDocument document) {
		Map<Token, Double> stats = new HashMap<Token, Double>();
		int docIndex = documentIndex.indexOf(document);
		for (int tokIndex = 0; tokIndex < tokenIndex.size(); tokIndex++) {
			double count = (Double) innerMatrix.get(docIndex, tokIndex);
			if (count != 0) {
				stats.put(tokenIndex.get(tokIndex), count);
			}
		}
		return stats;
	}

	public Map<SegmentedTextDocument, Double> getTokenStatistics(Token token) {
		Map<SegmentedTextDocument, Double> stats = new HashMap<SegmentedTextDocument, Double>();
		int tokIndex = tokenIndex.indexOf(token);
		for (int docIndex = 0; docIndex < documentIndex.size(); docIndex++) {
			double count = innerMatrix.get(docIndex, tokIndex);
			if (count != 0) {
				stats.put(documentIndex.get(docIndex), count);
			}
		}
		return stats;
	}

}
