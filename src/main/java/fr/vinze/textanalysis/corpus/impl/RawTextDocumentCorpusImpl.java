package fr.vinze.textanalysis.corpus.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.document.RawTextDocument;

public class RawTextDocumentCorpusImpl implements RawTextDocumentCorpus {

	private Map<String, RawTextDocument> documents;

	public RawTextDocumentCorpusImpl() {
		super();
		documents = new HashMap<String, RawTextDocument>();
	}

	public Collection<RawTextDocument> getDocuments() {
		return documents.values();
	}

	public RawTextDocument getDocument(String documentName) {
		return documents.get(documentName);
	}

	public void addDocument(RawTextDocument document) {
		documents.put(document.getName(), document);
	}

	public int getSize() {
		return documents.size();
	}
}
