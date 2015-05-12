package fr.vinze.textanalysis.matrix.tokenfrequency;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Table;

import fr.vinze.textanalysis.document.Punctuation.PunctuationMark;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.impl.PunctuationImpl;
import fr.vinze.textanalysis.document.impl.SegmentedTextDocumentImpl;
import fr.vinze.textanalysis.document.impl.SpecialToken;
import fr.vinze.textanalysis.document.impl.SpecialToken.TokenType;
import fr.vinze.textanalysis.document.impl.WordImpl;
import fr.vinze.textanalysis.matrix.tokenfrequency.TokenFrequencyMatrix;

public class TokenFrequencyMatrixTest {

	TokenFrequencyMatrix matrix;
	SegmentedTextDocument doc1, doc2, doc3;
	Token[] tok1, tok2, tok3;
	MutableInt[] counts1, counts2, counts3;
	Collection<Token> tokens;

	@Before
	public void init() throws Exception {
		// building a dummy matrix
		matrix = new TokenFrequencyMatrix(3, 6);
		doc1 = new SegmentedTextDocumentImpl("doc1", null);
		doc2 = new SegmentedTextDocumentImpl("doc2", null);
		doc3 = new SegmentedTextDocumentImpl("doc3", null);
		tok1 = new Token[] {
				new WordImpl("word1"),
				new WordImpl("word2"),
				new PunctuationImpl(PunctuationMark.COMMA),
				new WordImpl("word3"),
				new PunctuationImpl(PunctuationMark.DOT),
				new SpecialToken(TokenType.END_OF_DOCUMENT) };
		counts1 = new MutableInt[] {
				new MutableInt(1),
				new MutableInt(0),
				new MutableInt(2),
				null,
				new MutableInt(3),
				new MutableInt(1) };
		tok2 = new Token[] {
				new WordImpl("word1"),
				new WordImpl("word2"),
				new PunctuationImpl(PunctuationMark.COMMA),
				new WordImpl("word3"),
				new PunctuationImpl(PunctuationMark.DOT),
				new SpecialToken(TokenType.END_OF_DOCUMENT) };
		counts2 = new MutableInt[] {
				new MutableInt(0),
				new MutableInt(1),
				new MutableInt(3),
				new MutableInt(2),
				new MutableInt(1),
				new MutableInt(1) };
		tok3 = new Token[] {
				new WordImpl("word1"),
				new WordImpl("word2"),
				new PunctuationImpl(PunctuationMark.COMMA),
				new WordImpl("word3"),
				new PunctuationImpl(PunctuationMark.DOT),
				new SpecialToken(TokenType.END_OF_DOCUMENT) };
		counts3 = new MutableInt[] { new MutableInt(2), new MutableInt(1), null, null, new MutableInt(1),
				new MutableInt(1) };
		for (int i = 0; i < 6; i++) {
			matrix.setValue(doc1, tok1[i], counts1[i]);
			matrix.setValue(doc2, tok2[i], counts2[i]);
			matrix.setValue(doc3, tok3[i], counts3[i]);
		}
		tokens = matrix.getTokens();
	}

	@Test(timeout = 1000)
	public void testGetValue() throws Exception {
		MutableInt expected, actual;
		for (int i = 0; i < 6; i++) {
			expected = counts1[i];
			actual = matrix.getValue(doc1, tok1[i]);
			assertEquals("failed for doc1 and token " + i, expected == null ? null : expected.getValue(),
					actual == null ? null : actual.getValue());
			expected = counts2[i];
			actual = matrix.getValue(doc2, tok3[i]);
			assertEquals("failed for doc2 and token " + i, expected == null ? null : expected.getValue(),
					actual == null ? null : actual.getValue());
			expected = counts3[i];
			actual = matrix.getValue(doc3, tok2[i]);
			assertEquals("failed for doc3 and token " + i, expected == null ? null : expected.getValue(),
					actual == null ? null : actual.getValue());
		}
	}

	private Token searchEqualToken(Token token) {
		if (token == null) {
			return null;
		}
		for (Token candidate : tokens) {
			if (token.equals(candidate)) {
				return candidate;
			}
		}
		return null;
	}

	@Test(timeout = 1000)
	public void testAsTable() throws Exception {
		Table<SegmentedTextDocument, Token, MutableInt> table = matrix.asTable();
		MutableInt expected, actual;
		for (int i = 0; i < 6; i++) {
			Token tok = searchEqualToken(tok1[i]);
			expected = counts1[i];
			actual = table.get(doc1, tok);
			assertEquals("failed for doc1 and token " + i, expected == null ? null : expected.getValue(),
					actual == null ? null : actual.getValue());
			expected = counts2[i];
			actual = table.get(doc2, tok);
			assertEquals("failed for doc2 and token " + i, expected == null ? null : expected.getValue(),
					actual == null ? null : actual.getValue());
			expected = counts3[i];
			actual = table.get(doc3, tok);
			assertEquals("failed for doc3 and token " + i, expected == null ? null : expected.getValue(),
					actual == null ? null : actual.getValue());
		}
	}

	@Test(timeout = 1000)
	public void testGetDocumentStatistics() throws Exception {
		Token[] alltokens = new Token[] { tok1[0], tok1[1], tok1[2], tok2[3], tok1[4], tok1[5] };
		testGetDocumentStatistics(doc1, alltokens, counts1);
		testGetDocumentStatistics(doc2, alltokens, counts2);
		testGetDocumentStatistics(doc3, alltokens, counts3);
	}

	private void testGetDocumentStatistics(SegmentedTextDocument doc, Token[] tokens, MutableInt[] counts)
			throws Exception {
		Map<Token, MutableInt> documentStatistics = matrix.getDocumentStatistics(doc);
		for (int i = 0; i < 6; i++) {
			assertEquals("failed for document " + doc.getName() + " on token " + i, counts[i],
					documentStatistics.get(searchEqualToken(tokens[i])));
		}
	}

	@Test(timeout = 1000)
	public void testGetTokenStatistics() throws Exception {
		for (int i = 0; i < 6; i++) {
			testGetTokenStatistics(i);
		}
	}

	private void testGetTokenStatistics(int tokenIndex) throws Exception {
		if (tok1[tokenIndex] != null) {
			Map<SegmentedTextDocument, MutableInt> statistics = matrix.getTokenStatistics(tok1[tokenIndex]);
			assertEquals("count for document 1 on token " + tokenIndex, counts1[tokenIndex], statistics.get(doc1));
			assertEquals("count for document 2 on token " + tokenIndex, counts2[tokenIndex], statistics.get(doc2));
			assertEquals("count for document 3 on token " + tokenIndex, counts3[tokenIndex], statistics.get(doc3));
		} else if (tok2[tokenIndex] != null) {
			Map<SegmentedTextDocument, MutableInt> statistics = matrix.getTokenStatistics(tok2[tokenIndex]);
			assertEquals("count for document 1 on token " + tokenIndex, counts1[tokenIndex], statistics.get(doc1));
			assertEquals("count for document 2 on token " + tokenIndex, counts2[tokenIndex], statistics.get(doc2));
			assertEquals("count for document 3 on token " + tokenIndex, counts3[tokenIndex], statistics.get(doc3));
		} else if (tok3[tokenIndex] != null) {
			Map<SegmentedTextDocument, MutableInt> statistics = matrix.getTokenStatistics(tok3[tokenIndex]);
			assertEquals("count for document 1 on token " + tokenIndex, counts1[tokenIndex], statistics.get(doc1));
			assertEquals("count for document 2 on token " + tokenIndex, counts2[tokenIndex], statistics.get(doc2));
			assertEquals("count for document 3 on token " + tokenIndex, counts3[tokenIndex], statistics.get(doc3));
		}
	}

}
