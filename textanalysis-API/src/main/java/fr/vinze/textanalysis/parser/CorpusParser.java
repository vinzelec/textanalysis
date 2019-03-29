package fr.vinze.textanalysis.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;

/**
 * Corpus parser: Builds a set of documents from a corpus source
 *
 * @author Vinze
 */
public interface CorpusParser {

    RawTextDocumentCorpus parseCorpus(File source) throws ParseException, IOException;

    CorpusType canParse();

}
