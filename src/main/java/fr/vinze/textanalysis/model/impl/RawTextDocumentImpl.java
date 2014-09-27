package fr.vinze.textanalysis.model.impl;

import fr.vinze.textanalysis.document.RawTextDocument;

public class RawTextDocumentImpl implements RawTextDocument {

	final String content;
	
	public RawTextDocumentImpl(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
}
