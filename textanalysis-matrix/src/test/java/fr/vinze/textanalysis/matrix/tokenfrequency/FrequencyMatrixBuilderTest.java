package fr.vinze.textanalysis.matrix.tokenfrequency;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.vinze.textanalysis.corpus.CorpusUtils;
import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.corpus.impl.SegmentedTextDocumentCorpusImpl;
import fr.vinze.textanalysis.document.Punctuation.PunctuationMark;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.document.impl.PunctuationImpl;
import fr.vinze.textanalysis.document.impl.SegmentedTextDocumentImpl;
import fr.vinze.textanalysis.document.impl.SpecialToken;
import fr.vinze.textanalysis.document.impl.SpecialToken.TokenType;
import fr.vinze.textanalysis.document.impl.WordImpl;
import fr.vinze.textanalysis.mapper.impl.KeepOnlyWords;
import fr.vinze.textanalysis.mapper.impl.ToLowercase;
import fr.vinze.textanalysis.mapper.impl.TokenCounter;
import fr.vinze.textanalysis.matrix.DocumentTokenMatrixBuilder;

public class FrequencyMatrixBuilderTest {

	DocumentTokenMatrixBuilder<TokenFrequencyMatrix> builder;
	SegmentedTextDocumentCorpus corpus;

	Word[] words;
	int[] expectedCount;

	@Before
	public void init() throws Exception {
		builder = new TokenFrequencyMatrixBuilder();
		SegmentedTextDocumentCorpus tempCorpus = buildCorpus();
		// keep only words and to lower-case
		corpus = CorpusUtils.mapAll(tempCorpus, new KeepOnlyWords(), new ToLowercase());
		// three documents "test.txt", "test2.txt" and "test3.text"
		// each one has same content.
		words = new Word[]{
				new WordImpl("this"),
				new WordImpl("is"),
				new WordImpl("the"),
				new WordImpl("first"),
				new WordImpl("line"),
				new WordImpl("of"),
				new WordImpl("test"),
				new WordImpl("file"),
				new WordImpl("and"),
				new WordImpl("second")
		};
		expectedCount = new int[] { 2, 2, 3, 1, 1, 1, 1, 1, 1, 1 };
	}

	static List<Token> buildTokens() {
		List<Token> tokens = new ArrayList<Token>();
		tokens.add(new WordImpl("This"));
		tokens.add(new WordImpl("is"));
		tokens.add(new WordImpl("the"));
		tokens.add(new WordImpl("first"));
		tokens.add(new WordImpl("line"));
		tokens.add(new WordImpl("of"));
		tokens.add(new WordImpl("the"));
		tokens.add(new WordImpl("test"));
		tokens.add(new WordImpl("file"));
		tokens.add(new PunctuationImpl(PunctuationMark.DOT));
		tokens.add(new SpecialToken(TokenType.END_OF_PARAGRAPH));
		tokens.add(new WordImpl("And"));
		tokens.add(new WordImpl("this"));
		tokens.add(new WordImpl("is"));
		tokens.add(new WordImpl("the"));
		tokens.add(new WordImpl("second"));
		tokens.add(new PunctuationImpl(PunctuationMark.DOT));
		tokens.add(new SpecialToken(TokenType.END_OF_DOCUMENT));
		return tokens;
	}

	static SegmentedTextDocumentCorpus buildCorpus() {
		SegmentedTextDocumentCorpusImpl corpus = new SegmentedTextDocumentCorpusImpl();
		SegmentedTextDocumentImpl doc1 = new SegmentedTextDocumentImpl("test.txt", null);
		doc1.setTokens(buildTokens());
		corpus.addDocument(doc1);
		SegmentedTextDocumentImpl doc2 = new SegmentedTextDocumentImpl("test2.txt", null);
		doc2.setTokens(buildTokens());
		corpus.addDocument(doc2);
		SegmentedTextDocumentImpl doc3 = new SegmentedTextDocumentImpl("test3.text", null);
		doc3.setTokens(buildTokens());
		corpus.addDocument(doc3);
		return corpus;
	}

	@Test(timeout = 1000)
	public void testMatrixBuilderWithoutTermCount() throws Exception {
		TokenFrequencyMatrix matrix = builder.computeMatrix(corpus);
		testMatrix(matrix);
	}

	@Test(timeout = 1000)
	public void testMatrixBuilder() throws Exception {
		corpus = CorpusUtils.map(corpus, new TokenCounter());
		TokenFrequencyMatrix matrix = builder.computeMatrix(corpus);
		testMatrix(matrix);
	}

	private void testMatrix(TokenFrequencyMatrix matrix) {
		for (int i = 0; i < words.length; i++) {
			for (SegmentedTextDocument doc : matrix.getDocuments()) {
				assertEquals("count for doc " + (doc.getName()) + " and word " + i, expectedCount[i],
						matrix.getValue(doc, words[i]).intValue());
			}
		}
	}

}
