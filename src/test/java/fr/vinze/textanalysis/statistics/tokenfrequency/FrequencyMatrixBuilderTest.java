package fr.vinze.textanalysis.statistics.tokenfrequency;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fr.vinze.textanalysis.corpus.CorpusUtils;
import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.DocumentTestHelper;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.document.impl.WordImpl;
import fr.vinze.textanalysis.mapper.impl.KeepOnlyWords;
import fr.vinze.textanalysis.mapper.impl.ToLowercase;
import fr.vinze.textanalysis.mapper.impl.TokenCounter;
import fr.vinze.textanalysis.statistics.DocumentTokenMatrixBuilder;
import fr.vinze.textanalysis.statistics.tokenfrequency.TokenFrequencyMatrix;
import fr.vinze.textanalysis.statistics.tokenfrequency.TokenFrequencyMatrixBuilder;

public class FrequencyMatrixBuilderTest {

	DocumentTokenMatrixBuilder<TokenFrequencyMatrix> builder;
	SegmentedTextDocumentCorpus corpus;

	Word[] words;
	int[] expectedCount;

	@Before
	public void init() throws Exception {
		builder = new TokenFrequencyMatrixBuilder();
		SegmentedTextDocumentCorpus tempCorpus = DocumentTestHelper.createTestSegmentedCorpus();
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
				assertEquals("count for doc" + (doc.getName()) + " and word " + i, expectedCount[i],
						matrix.getValue(doc, words[i]).intValue());
			}
		}
	}

}
