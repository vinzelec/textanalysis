package fr.vinze.textanalysis.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;

public interface CorpusParser {

	public RawTextDocumentCorpus parseCorpus(File source)
			throws FileNotFoundException,
			ParseException, IOException;

	public CorpusType canParse();

}
