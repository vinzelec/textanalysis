package fr.vinze.textanalysis.corpus.impl;

import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.SegmentedTextDocument;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SegmentedTextDocumentCorpusImpl implements
		SegmentedTextDocumentCorpus {

	Map<String, SegmentedTextDocument> documents;
	private String id;

	@Override
	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}

	public SegmentedTextDocumentCorpusImpl() {
		super();
		documents = new HashMap<>();
	}

	@Override
	public Collection<SegmentedTextDocument> getDocuments() {
		return documents.values();
	}

	@Override
	public SegmentedTextDocument getDocument(String documentName) {
		return documents.get(documentName);
	}

	@Override
	public void addDocument(SegmentedTextDocument document) {
		documents.put(document.getName(), document);
	}

	@Override
	public int getSize() {
		return documents.size();
	}

}
