package org.textanalysis.corpus.nosql;

import java.util.Collection;

import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.corpus.persistence.SegmentedTextDocumentCorpusDAO;
import fr.vinze.textanalysis.document.persistence.SegmentedTextDocumentDAO;

public class SegmentedTextDocumentCorpusDAOImpl implements SegmentedTextDocumentCorpusDAO {

	final SegmentedTextDocumentDAO documentDAO;

	public SegmentedTextDocumentCorpusDAOImpl(SegmentedTextDocumentDAO documentDAO) {
		super();
		this.documentDAO = documentDAO;
	}

	@Override
	public SegmentedTextDocumentDAO getDocumentDAO() {
		return documentDAO;
	}

	@Override
	public SegmentedTextDocumentCorpus saveOrCreate(SegmentedTextDocumentCorpus obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SegmentedTextDocumentCorpus get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SegmentedTextDocumentCorpus> list(Collection<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAll(Collection<SegmentedTextDocumentCorpus> collection) {
		// TODO Auto-generated method stub

	}
}
