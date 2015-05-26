package org.textanalysis.corpus.nosql;

import java.util.Collection;

import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.corpus.persistence.RawTextDocumentCorpusDAO;
import fr.vinze.textanalysis.document.persistence.RawTextDocumentDAO;

public class RawTextDocumentCorpusDAOImpl implements RawTextDocumentCorpusDAO {

	final RawTextDocumentDAO documentDAO;

	public RawTextDocumentCorpusDAOImpl(RawTextDocumentDAO documentDAO) {
		super();
		this.documentDAO = documentDAO;
	}

	@Override
	public RawTextDocumentDAO getDocumentDAO() {
		return documentDAO;
	}

	@Override
	public RawTextDocumentCorpus saveOrCreate(RawTextDocumentCorpus obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RawTextDocumentCorpus get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<RawTextDocumentCorpus> list(Collection<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAll(Collection<RawTextDocumentCorpus> collection) {
		// TODO Auto-generated method stub

	}
}
