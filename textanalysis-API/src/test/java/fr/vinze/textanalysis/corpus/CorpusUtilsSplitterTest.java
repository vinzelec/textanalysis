package fr.vinze.textanalysis.corpus;

import fr.vinze.textanalysis.document.DocumentTestHelper;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.segmentation.Splitter;
import fr.vinze.textanalysis.segmentation.impl.TextSplitterImpl;
import org.junit.Test;

public class CorpusUtilsSplitterTest extends AbstractCorpusUtilsTest {

    @Test(timeout = 1000)
    public void testSplit() throws Exception {
        Splitter splitter = new TextSplitterImpl();
        // corpus then split
        RawTextDocumentCorpus corpusIn = CorpusUtils.createCorpus(rawdoc11, rawdoc12);
        SegmentedTextDocumentCorpus corpus1 = CorpusUtils.split(corpusIn, splitter);
        // split then corpus
        SegmentedTextDocument doc21 = splitter.apply(rawdoc21);
        SegmentedTextDocument doc22 = splitter.apply(rawdoc22);
        SegmentedTextDocumentCorpus corpus2 = CorpusUtils.createCorpus(doc21, doc22);
        // test same result
        DocumentTestHelper.assertDocumentsEquals(corpus1.getDocument(NAME), corpus2.getDocument(NAME));
        DocumentTestHelper.assertDocumentsEquals(corpus1.getDocument(NAME + "2"),
                corpus2.getDocument(NAME + "2"));
    }

}
