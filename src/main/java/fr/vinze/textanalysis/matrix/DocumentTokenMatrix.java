package fr.vinze.textanalysis.matrix;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Table;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;

/**
 * <p>
 * A document*token matrix (associate a statistical value to each couple
 * document/token in a corpus).
 * </p>
 * <p>
 * In general {@link #getValue(SegmentedTextDocument, Token)} and
 * {@link #setValue(SegmentedTextDocument, Token, Number)} are to be preferred
 * over other cosmetic functions that may have performance issues to build from
 * inner representation.
 * </p>
 * 
 * @author Vinze
 *
 * @param <T>
 */
public interface DocumentTokenMatrix<T extends Number> {

	T getValue(SegmentedTextDocument document, Token token);

	void setValue(SegmentedTextDocument document, Token token, T value);

	Collection<SegmentedTextDocument> getDocuments();

	Collection<Token> getTokens();

	Table<SegmentedTextDocument, Token, T> asTable();

	Map<Token, T> getDocumentStatistics(SegmentedTextDocument document);

	Map<SegmentedTextDocument, T> getTokenStatistics(Token token);

}
