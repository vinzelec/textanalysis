package fr.vinze.textanalysis.statistics.tokenfrequency;

import org.apache.commons.lang3.mutable.MutableInt;

import cern.colt.matrix.impl.SparseObjectMatrix2D;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.statistics.impl.AbstractColtObjectMatrix;

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
public class TokenFrequencyMatrix extends AbstractColtObjectMatrix<MutableInt> {

	public TokenFrequencyMatrix(int initialDocumentSize, int initialTokenSize) {
		super(initialDocumentSize, initialTokenSize);
	}

	SparseObjectMatrix2D getInnerMatrix() {
		return this.innerMatrix;
	}

}
