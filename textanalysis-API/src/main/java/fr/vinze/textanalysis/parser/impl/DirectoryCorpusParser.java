package fr.vinze.textanalysis.parser.impl;

import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.corpus.impl.RawTextDocumentCorpusImpl;
import fr.vinze.textanalysis.parser.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DirectoryCorpusParser implements CorpusParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryCorpusParser.class);

	@Override
	public RawTextDocumentCorpus parseCorpus(File source) throws ParseException, IOException {
		if (source == null || !source.exists()) {
			throw new FileNotFoundException("The source " + source
					+ " does not exists");
		}
		if (!source.isDirectory()) {
			throw new ParseException("input file is not a directory");
		}
		RawTextDocumentCorpus corpus = new RawTextDocumentCorpusImpl();
		for(File file : source.listFiles()) {
			 try {
				DocumentParser parser = DocumentParserFactory.getParser(file);
				corpus.addDocument(parser.parse(file));
			} catch (DocumentParserNotAvailable e) {
				 LOGGER.debug("ignoring entry {} not parser found", file, e);
			} catch (DocumentTypeNotSupported e) {
				 LOGGER.debug("ignoring entry {} type not supported", file, e);
			}
		}
		return corpus;
	}

	@Override
	public CorpusType canParse() {
		return CorpusType.DIR;
	}

}
