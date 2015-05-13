package fr.vinze.textanalysis.parser.impl;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.parser.CorpusType;
import fr.vinze.textanalysis.parser.ParseException;

public class EPUBCorpusParser extends ZIPCorpusParser {

	@Override
	protected void processEntry(ZipEntry entry, ZipInputStream in, RawTextDocumentCorpus corpus) throws IOException,
			ParseException {
		// only processing xhtml files
		if (entry.getName().endsWith("xhtml")) {
			super.processEntry(entry, in, corpus);
		}
	}

	@Override
	public CorpusType canParse() {
		return CorpusType.EPUB;
	}

}
