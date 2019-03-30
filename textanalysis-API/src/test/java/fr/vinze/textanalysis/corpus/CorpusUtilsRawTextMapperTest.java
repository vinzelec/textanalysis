package fr.vinze.textanalysis.corpus;

import fr.vinze.textanalysis.document.DocumentTestHelper;
import fr.vinze.textanalysis.mapper.RawTextMapper;
import fr.vinze.textanalysis.mapper.impl.PunctuationCleaner;
import fr.vinze.textanalysis.mapper.impl.ReturnCarriageCleaner;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CorpusUtilsRawTextMapperTest extends AbstractCorpusUtilsTest {

    @Test(timeout = 1000)
    public void testMapDocument() throws Exception {
        testMap(new PunctuationCleaner());
    }

    @Test(timeout = 1000)
    public void testMapAllDocument() throws Exception {
        testMap(new PunctuationCleaner(), new ReturnCarriageCleaner());
    }

    private void testMap(RawTextMapper... mappers) {
        assertTrue(mappers.length > 0);
        // apply then corpus
        for (RawTextMapper mapper : mappers) {
            rawdoc11 = mapper.apply(rawdoc11);
            rawdoc12 = mapper.apply(rawdoc12);
        }
        RawTextDocumentCorpus corpus1 = CorpusUtils.createCorpus(rawdoc11, rawdoc12);
        // corpus then apply
        RawTextDocumentCorpus corpus2 = CorpusUtils.createCorpus(rawdoc21, rawdoc22);
        if (mappers.length == 1) {
            corpus2 = CorpusUtils.map(corpus2, mappers[0]);
        } else {
            corpus2 = CorpusUtils.mapAll(corpus2, mappers);
        }
        // test same result
        DocumentTestHelper.assertDocumentsEquals(corpus1.getDocument(NAME), corpus2.getDocument(NAME));
        DocumentTestHelper.assertDocumentsEquals(corpus1.getDocument(NAME + "2"), corpus2.getDocument(NAME + "2"));
    }

}
