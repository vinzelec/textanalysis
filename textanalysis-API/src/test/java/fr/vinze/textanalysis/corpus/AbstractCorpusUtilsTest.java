package fr.vinze.textanalysis.corpus;

import fr.vinze.textanalysis.document.DocumentTestHelper;
import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import org.junit.Before;

public class AbstractCorpusUtilsTest {

    final static String NAME = "documentName";
    private final static String CONTENT = "This «test» \r\nis to be cleaned...";

    RawTextDocument rawdoc11;
    RawTextDocument rawdoc21;
    RawTextDocument rawdoc12;
    RawTextDocument rawdoc22;

    @Before
    public void init() {
        // not used by testCreateCorpus but all others...
        rawdoc11 = new RawTextDocumentImpl(NAME, CONTENT);
        rawdoc21 = new RawTextDocumentImpl(NAME, CONTENT);
        rawdoc12 = new RawTextDocumentImpl(NAME + "2", CONTENT);
        rawdoc22 = new RawTextDocumentImpl(NAME + "2", CONTENT);
        DocumentTestHelper.assertDocumentsEquals(rawdoc11, rawdoc21);
        DocumentTestHelper.assertDocumentsEquals(rawdoc12, rawdoc22);

    }

}
