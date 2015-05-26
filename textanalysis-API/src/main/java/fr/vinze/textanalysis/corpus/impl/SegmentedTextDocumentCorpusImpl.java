package fr.vinze.textanalysis.corpus.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.SegmentedTextDocument;

public class SegmentedTextDocumentCorpusImpl implements
		SegmentedTextDocumentCorpus {

	Map<String, SegmentedTextDocument> documents;
	private String id;

	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}

	public SegmentedTextDocumentCorpusImpl() {
		super();
		documents = new HashMap<String, SegmentedTextDocument>();
	}

	public Collection<SegmentedTextDocument> getDocuments() {
		return documents.values();
	}

	public SegmentedTextDocument getDocument(String documentName) {
		return documents.get(documentName);
	}

	public void addDocument(SegmentedTextDocument document) {
		documents.put(document.getName(), document);
	}

	public int getSize() {
		return documents.size();
	}

}
