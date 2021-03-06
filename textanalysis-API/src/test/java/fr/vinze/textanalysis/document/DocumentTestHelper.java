package fr.vinze.textanalysis.document;

import fr.vinze.textanalysis.corpus.CorpusUtils;
import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.parser.*;
import fr.vinze.textanalysis.parser.impl.DirectoryCorpusParser;
import fr.vinze.textanalysis.parser.impl.TxtDocumentParser;
import fr.vinze.textanalysis.segmentation.Splitter;
import fr.vinze.textanalysis.segmentation.impl.TextSplitterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public abstract class DocumentTestHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentTestHelper.class);

    private final static File corpusTextFolder;
    private final static File documentText;

    static {
        URL urlToFile = DocumentTestHelper.class.getResource("/testFolder");
        corpusTextFolder = new File(urlToFile.getPath());
        urlToFile = DocumentTestHelper.class.getResource("/test.txt");
        documentText = new File(urlToFile.getPath());
    }

    private static void initParsers() {
        // document parsers
        try {
            DocumentParserFactory.getParser(DocumentType.TXT);
        } catch (DocumentParserNotAvailable e) {
            LOGGER.debug("first use : init the document parser for txt files");
            TxtDocumentParser txtParser = new TxtDocumentParser();
            DocumentParserFactory.registerParser(txtParser);
        }
        // corpus parsers
        try {
            CorpusFactory.getParser(CorpusType.DIR);
        } catch (DocumentParserNotAvailable e) {
            LOGGER.debug("first use : init the corpus parser for folders");
            DirectoryCorpusParser corpusParser = new DirectoryCorpusParser();
            CorpusFactory.registerParser(corpusParser);
        }
    }

    public static RawTextDocumentCorpus createTestCorpus() throws ParseException, IOException,
            DocumentParserNotAvailable, DocumentTypeNotSupported {
        initParsers();
        CorpusParser corpusParser = CorpusFactory.getParser(corpusTextFolder);
        return corpusParser.parseCorpus(corpusTextFolder);
    }

    public static RawTextDocument createTestDocument() throws ParseException, IOException,
            DocumentParserNotAvailable, DocumentTypeNotSupported {
        initParsers();
        DocumentParser parser = DocumentParserFactory.getParser(documentText);
        return parser.parse(documentText);
    }

    public static SegmentedTextDocumentCorpus createTestSegmentedCorpus() throws DocumentParserNotAvailable,
            DocumentTypeNotSupported, ParseException, IOException {
        RawTextDocumentCorpus src = createTestCorpus();
        Splitter splitter = new TextSplitterImpl();
        return CorpusUtils.split(src, splitter);
    }

    public static SegmentedTextDocument createTestSegmentedDocument() throws ParseException,
            IOException, DocumentParserNotAvailable, DocumentTypeNotSupported {
        RawTextDocument src = createTestDocument();
        Splitter splitter = new TextSplitterImpl();
        return splitter.apply(src);
    }

    public static void assertDocumentsEquals(RawTextDocument doc1, RawTextDocument doc2) {
        assertEquals(doc1.getName(), doc2.getName());
        assertEquals(doc1.getContent(), doc2.getContent());
    }

    public static void assertDocumentsEquals(SegmentedTextDocument doc1, SegmentedTextDocument doc2) {
        assertEquals(doc1.getName(), doc2.getName());
        assertEquals(doc1.getTokens().size(), doc2.getTokens().size());
        for (int i = 0; i < doc1.getTokens().size(); i++) {
            assertEquals(doc1.getTokens().get(i), doc2.getTokens().get(i));
        }
    }

    // TODO methods to compare equality of corpus
}
