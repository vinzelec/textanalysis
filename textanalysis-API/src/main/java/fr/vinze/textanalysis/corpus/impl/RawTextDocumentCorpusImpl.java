package fr.vinze.textanalysis.corpus.impl;

import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.document.RawTextDocument;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RawTextDocumentCorpusImpl implements RawTextDocumentCorpus {

	private Map<String, RawTextDocument> documents;
	private String id;

	@Override
	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}

	public RawTextDocumentCorpusImpl() {
		super();
		documents = new HashMap<>();
	}
	@Override
	public Collection<RawTextDocument> getDocuments() {
		return documents.values();
	}

	@Override
	public RawTextDocument getDocument(String documentName) {
		return documents.get(documentName);
	}

	@Override
	public void addDocument(RawTextDocument document) {
		documents.put(document.getName(), document);
	}

	@Override
	public int getSize() {
		return documents.size();
	}
}
