package fr.vinze.textanalysis.corpus.impl;

import java.util.ArrayList;
import java.util.Collection;

import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.document.RawTextDocument;

public class RawTextDocumentCorpusImpl implements RawTextDocumentCorpus {

	private Collection<RawTextDocument> documents;

	public RawTextDocumentCorpusImpl() {
		super();
		documents = new ArrayList<RawTextDocument>();
	}

	public Collection<RawTextDocument> getDocuments() {
		return documents;
	}

	public void addDocument(RawTextDocument document) {
		documents.add(document);
	}
}
