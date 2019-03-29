package fr.vinze.textanalysis.corpus;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Test case for {@link CorpusUtils#createCorpus(List)} and {@link CorpusUtils#createCorpus(RawTextDocument...)}
 *
 * @author Vinze
 */
public class CorpusUtilsCreationTest {

    @Test(timeout = 1000)
    public void testCreateCorpusList() {
        String[][] map = new String[][]{
                new String[]{"doc1", "content1"},
                new String[]{"doc2", "content2"},
                new String[]{"doc3", "content3"},
                new String[]{"doc4", "content4"},
                new String[]{"doc5", "content5"},
                new String[]{"doc6", "content6"}
        };
        List<RawTextDocument> documents = Arrays.stream(map).map(line -> new RawTextDocumentImpl(line[0], line[1])).collect(Collectors.toList());
        // build corpus
        RawTextDocumentCorpus corpus = CorpusUtils.createCorpus(documents);
        // test content
        Arrays.stream(map).forEach(line -> testRawDocumentContent(corpus.getDocument(line[0]), line[0], line[1]));
        assertNull(corpus.getDocument("doc7"));
    }

    @Test(timeout = 1000)
    public void testCreateCorpusArray() {
        String[][] map = new String[][]{
                new String[]{"doc1", "content1"},
                new String[]{"doc2", "content2"},
                new String[]{"doc3", "content3"},
                new String[]{"doc4", "content4"},
                new String[]{"doc5", "content5"},
                new String[]{"doc6", "content6"}
        };
        RawTextDocument[] documents = Arrays.stream(map).map(line -> new RawTextDocumentImpl(line[0], line[1])).collect(Collectors.toList()).toArray(new RawTextDocument[6]);
        // build corpus
        RawTextDocumentCorpus corpus = CorpusUtils.createCorpus(documents);
        // test content
        Arrays.stream(map).forEach(line -> testRawDocumentContent(corpus.getDocument(line[0]), line[0], line[1]));
        assertNull(corpus.getDocument("doc7"));
    }

    private void testRawDocumentContent(RawTextDocument doc, String expectedName, String expectedContent) {
        assertNotNull(doc);
        assertEquals(expectedName, doc.getName());
        assertEquals(expectedContent, doc.getContent());
    }

}
