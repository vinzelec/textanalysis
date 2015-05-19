package fr.vinze.textanalysis.document.impl;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import fr.vinze.textanalysis.document.RawTextDocument;

public class RawTextDocumentImpl implements RawTextDocument {

	String name;
	final String content, rawSource;
	
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

	public void setName(String name) {
		this.name = name;
	}

	public String getRawSource() {
		return rawSource;
	}

	public String getUniqueID() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof RawTextDocument)) {
			return false;
		}
		EqualsBuilder eb = new EqualsBuilder();
		eb.append(getName(), ((RawTextDocument) obj).getName());
		return eb.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
		hashCodeBuilder.append(getName());
		return super.hashCode();
	}

}
