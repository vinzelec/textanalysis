package fr.vinze.textanalysis.parser;

import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;

import java.io.File;
import java.io.IOException;

/**
 * Corpus parser: Builds a set of documents from a corpus source
 *
 * @author Vinze
 */
public interface CorpusParser {

    RawTextDocumentCorpus parseCorpus(File source) throws ParseException, IOException;

    CorpusType canParse();

}
