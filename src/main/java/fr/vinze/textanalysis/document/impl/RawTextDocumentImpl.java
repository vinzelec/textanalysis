package fr.vinze.textanalysis.document.impl;

import fr.vinze.textanalysis.document.RawTextDocument;

public class RawTextDocumentImpl implements RawTextDocument {

	final String name, content, rawSource;
	
	public RawTextDocumentImpl(String name, String content) {
		super();
		this.name = name;
		this.content = content;
		this.rawSource = content;
	}

	public RawTextDocumentImpl(String name, String content, String rawSource) {
		super();
		this.name = name;
		this.content = content;
		this.rawSource = rawSource;
	}

	public String getContent() {
		return content;
	}

	public String getName() {
		return name;
	}

	public String getRawSource() {
		return rawSource;
	}

}
