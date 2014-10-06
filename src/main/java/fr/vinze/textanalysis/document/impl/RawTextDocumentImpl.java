package fr.vinze.textanalysis.document.impl;

import fr.vinze.textanalysis.document.RawTextDocument;

public class RawTextDocumentImpl implements RawTextDocument {

	final String name, content;
	
	public RawTextDocumentImpl(String name, String content) {
		super();
		this.name = name;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public String getName() {
		return name;
	}
	
}
