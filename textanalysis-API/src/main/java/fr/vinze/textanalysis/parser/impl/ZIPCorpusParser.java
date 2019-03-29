package fr.vinze.textanalysis.parser.impl;

import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.corpus.impl.RawTextDocumentCorpusImpl;
import fr.vinze.textanalysis.parser.*;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZIPCorpusParser implements CorpusParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZIPCorpusParser.class);

	@Override
	public RawTextDocumentCorpus parseCorpus(File source) throws FileNotFoundException, ParseException, IOException {

		try (ZipInputStream in = new ZipInputStream(new FileInputStream(source))) {
			RawTextDocumentCorpus corpus = new RawTextDocumentCorpusImpl();
			ZipEntry entry;
			while ((entry = in.getNextEntry()) != null) {
				processEntry(entry, in, corpus);
			}
			return corpus;
		}
	}

	protected void processEntry(ZipEntry entry, ZipInputStream in, RawTextDocumentCorpus corpus) throws IOException,
			ParseException {
		byte[] buffer = new byte[1024];
		DocumentParser parser = null;
		String name = entry.getName();
		name = FilenameUtils.getName(name); // remove path to keep file name
		LOGGER.debug("processing entry {}", name);
		try {
			parser = DocumentParserFactory.getParser(name);
			// extract data
			StringBuilder content = new StringBuilder();
			// open output streams
			while (in.read(buffer) > 0) {
				content.append(new String(buffer));
			}
			corpus.addDocument(parser.parse(name, postprocessContent(content)));
		} catch (DocumentParserNotAvailable | DocumentTypeNotSupported e) {
			LOGGER.debug("ignoring entry {}", name, e);
		}
	}

	protected String postprocessContent(StringBuilder content) {
		return content.toString();
	}

	@Override
	public CorpusType canParse() {
		return CorpusType.ZIP;
	}

}
