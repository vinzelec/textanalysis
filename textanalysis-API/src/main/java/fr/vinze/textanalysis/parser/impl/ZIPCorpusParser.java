package fr.vinze.textanalysis.parser.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.corpus.impl.RawTextDocumentCorpusImpl;
import fr.vinze.textanalysis.parser.CorpusParser;
import fr.vinze.textanalysis.parser.CorpusType;
import fr.vinze.textanalysis.parser.DocumentParser;
import fr.vinze.textanalysis.parser.DocumentParserFactory;
import fr.vinze.textanalysis.parser.DocumentParserNotAvailable;
import fr.vinze.textanalysis.parser.DocumentTypeNotSupported;
import fr.vinze.textanalysis.parser.ParseException;

public class ZIPCorpusParser implements CorpusParser {

	private static final Logger log = LoggerFactory.getLogger(ZIPCorpusParser.class);

	@Override
	public RawTextDocumentCorpus parseCorpus(File source) throws FileNotFoundException, ParseException, IOException {
		ZipInputStream in = null;
		try {
			RawTextDocumentCorpus corpus = new RawTextDocumentCorpusImpl();
			in = new ZipInputStream(new FileInputStream(source));
			byte[] buffer = new byte[1024];
			ZipEntry entry;
			while ((entry = in.getNextEntry()) != null) {
				String name = entry.getName();
				// extract data
				StringBuilder content = new StringBuilder();
				// open output streams
				while (in.read(buffer) > 0) {
					content.append(new String(buffer));
				}
				try {
					DocumentParser parser = DocumentParserFactory.getParser(name);
					corpus.addDocument(parser.parse(name, content.toString()));
				} catch (DocumentParserNotAvailable | DocumentTypeNotSupported e) {
					log.debug("ignoring entry " + name, e);
				}
			}
			in.close();
			return corpus;
		} finally {
			if (in != null) {
				IOUtils.closeQuietly(in);
			}
		}
	}

	@Override
	public CorpusType canParse() {
		return CorpusType.ZIP;
	}

}
