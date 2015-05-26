package fr.vinze.textanalysis.corpus.persistence;

import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.persistence.SegmentedTextDocumentDAO;
import fr.vinze.textanalysis.persistence.DAO;

public interface SegmentedTextDocumentCorpusDAO extends DAO<SegmentedTextDocumentCorpus> {

	/**
	 * @return the DAO used to persist and retrieve the documents within the corpus
	 */
	SegmentedTextDocumentDAO getDocumentDAO();

	// TODO specific methods on corpus ?

}
