package fr.vinze.textanalysis.corpus;

import fr.vinze.textanalysis.document.DocumentTestHelper;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.mapper.SegmentedTextMapper;
import fr.vinze.textanalysis.mapper.impl.KeepOnlyWords;
import fr.vinze.textanalysis.mapper.impl.SegmentedToLowercase;
import fr.vinze.textanalysis.segmentation.Splitter;
import fr.vinze.textanalysis.segmentation.impl.TextSplitterImpl;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CorpusUtilsSegmentedTextMapperTest extends AbstractCorpusUtilsTest {


    @Test(timeout = 1000)
    public void testMapSegmented() throws Exception {
        testMap(new KeepOnlyWords());
    }

    @Test(timeout = 1000)
    public void testMapAllSegmented() throws Exception {
        testMap(new KeepOnlyWords(), new SegmentedToLowercase());
    }

    private void testMap(SegmentedTextMapper... mappers) {
        assertTrue(mappers.length > 0);
        // segment
        Splitter splitter = new TextSplitterImpl();
        SegmentedTextDocument doc11 = splitter.apply(rawdoc11);
        SegmentedTextDocument doc21 = splitter.apply(rawdoc21);
        SegmentedTextDocument doc12 = splitter.apply(rawdoc12);
        SegmentedTextDocument doc22 = splitter.apply(rawdoc22);
        DocumentTestHelper.assertDocumentsEquals(doc11, doc21);
        DocumentTestHelper.assertDocumentsEquals(doc12, doc22);
        // apply then create a corpus
        for (SegmentedTextMapper mapper : mappers) {
            doc11 = mapper.apply(doc11);
            doc12 = mapper.apply(doc12);
        }
        SegmentedTextDocumentCorpus corpus1 = CorpusUtils.createCorpus(doc11, doc12);
        // create a corpus then apply
        SegmentedTextDocumentCorpus corpus2 = CorpusUtils.createCorpus(doc21, doc22);
        if (mappers.length == 1) {
            corpus2 = CorpusUtils.map(corpus2, mappers[0]);
        } else {
            corpus2 = CorpusUtils.mapAll(corpus2, mappers);
        }
        // test same result
        DocumentTestHelper.assertDocumentsEquals(corpus1.getDocument(NAME), corpus2.getDocument(NAME));
        DocumentTestHelper.assertDocumentsEquals(corpus1.getDocument(NAME + "2"),
                corpus2.getDocument(NAME + "2"));
    }

}
