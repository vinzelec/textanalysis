package fr.vinze.textanalysis.document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.vinze.textanalysis.corpus.CorpusUtils;
import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.parser.CorpusFactory;
import fr.vinze.textanalysis.parser.CorpusParser;
import fr.vinze.textanalysis.parser.CorpusType;
import fr.vinze.textanalysis.parser.DocumentParser;
import fr.vinze.textanalysis.parser.DocumentParserFactory;
import fr.vinze.textanalysis.parser.DocumentParserNotAvailable;
import fr.vinze.textanalysis.parser.DocumentType;
import fr.vinze.textanalysis.parser.DocumentTypeNotSupported;
import fr.vinze.textanalysis.parser.ParseException;
import fr.vinze.textanalysis.parser.impl.DirectoryCorpusParser;
import fr.vinze.textanalysis.parser.impl.TxtDocumentParser;
import fr.vinze.textanalysis.segmentation.Splitter;
import fr.vinze.textanalysis.segmentation.impl.TextSplitterImpl;

public abstract class DocumentTestHelper {

	private static final Logger log = LoggerFactory.getLogger(DocumentTestHelper.class);

	static File corpusTextFolder, documentText;

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
			log.debug("first use : init the document parser for txt files");
			TxtDocumentParser txtParser = new TxtDocumentParser();
			DocumentParserFactory.registerParser(txtParser);
		}
		// corpus parsers
		try {
			CorpusFactory.getParser(CorpusType.DIR);
		} catch (DocumentParserNotAvailable e) {
			log.debug("first use : init the corpus parser for folders");
			DirectoryCorpusParser corpusParser = new DirectoryCorpusParser();
			CorpusFactory.registerParser(corpusParser);
		}
	}

	public static RawTextDocumentCorpus createTestCorpus() throws FileNotFoundException, ParseException, IOException,
			DocumentParserNotAvailable, DocumentTypeNotSupported {
		initParsers();
		CorpusParser corpusParser = CorpusFactory.getParser(corpusTextFolder);
		return corpusParser.parseCorpus(corpusTextFolder);
	}

	public static RawTextDocument createTestDocument() throws FileNotFoundException, ParseException, IOException,
			DocumentParserNotAvailable, DocumentTypeNotSupported {
		initParsers();
		DocumentParser parser = DocumentParserFactory.getParser(documentText);
		return parser.parse(documentText);
	}

	public static SegmentedTextDocumentCorpus createTestSegmentedCorpus() throws FileNotFoundException,
			DocumentParserNotAvailable, DocumentTypeNotSupported, ParseException, IOException {
		RawTextDocumentCorpus src = createTestCorpus();
		Splitter splitter = new TextSplitterImpl();
		return CorpusUtils.split(src, splitter);
	}

	public static SegmentedTextDocument createTestSegmentedDocument() throws FileNotFoundException, ParseException,
			IOException, DocumentParserNotAvailable, DocumentTypeNotSupported {
		RawTextDocument src = createTestDocument();
		Splitter splitter = new TextSplitterImpl();
		return splitter.split(src);
	}

}
