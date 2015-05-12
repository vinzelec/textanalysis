package fr.vinze.textanalysis.parser.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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

public class DirectoryCorpusParser implements CorpusParser {

	static Logger log = LoggerFactory.getLogger(DirectoryCorpusParser.class);

	public RawTextDocumentCorpus parseCorpus(File source)
			throws FileNotFoundException, ParseException, IOException {
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
				log.debug("ignoring entry " + file + " not parser found", e);
			} catch (DocumentTypeNotSupported e) {
				log.debug("ignoring entry " + file + " type not supported", e);
			}
		}
		return corpus;
	}

	public CorpusType canParse() {
		return CorpusType.DIR;
	}

}
