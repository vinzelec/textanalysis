package fr.vinze.textanalysis.corpus.persistence;

import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.document.persistence.RawTextDocumentDAO;
import fr.vinze.textanalysis.persistence.DAO;

public interface RawTextDocumentCorpusDAO extends DAO<RawTextDocumentCorpus> {

	/**
	 * @return the DAO used to persist and retrieve the documents within the corpus
	 */
	RawTextDocumentDAO getDocumentDAO();

	// TODO specific methods on corpus ?


}
