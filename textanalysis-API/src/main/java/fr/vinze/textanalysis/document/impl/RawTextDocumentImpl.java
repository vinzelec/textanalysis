package fr.vinze.textanalysis.document.impl;

import fr.vinze.textanalysis.document.RawTextDocument;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RawTextDocumentImpl implements RawTextDocument {

	private String name;
	private final String content;
	private final String rawSource;
	private String id;

	@Override
	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}
	
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

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getRawSource() {
		return rawSource;
	}

	@Override
	public String getUniqueID() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RawTextDocument)) {
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
