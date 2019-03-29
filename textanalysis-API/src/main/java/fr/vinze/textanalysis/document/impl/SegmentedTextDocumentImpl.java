package fr.vinze.textanalysis.document.impl;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

public class SegmentedTextDocumentImpl implements SegmentedTextDocument {

	private final RawTextDocument source;
	private String name;
	private List<Token> tokens;
	private Integer tokenCount = null;
	private String id;

	@Override
	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}

	public SegmentedTextDocumentImpl(RawTextDocument source) {
		this(source.getName(), source);
	}

	public SegmentedTextDocumentImpl(String name, RawTextDocument source) {
		super();
		this.source = source;
		this.name = name;
		tokens = new ArrayList<>();
	}

	@Override
	public RawTextDocument getSource() {
		return source;
	}

	@Override
	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setTokenCount(int count) {
		tokenCount = count;
	}

	@Override
	public int getTokenCount() {
		if (tokenCount == null) {
			return tokens.size();
		}
		return tokenCount;
	}

	@Override
	public String getUniqueID() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof SegmentedTextDocument)) {
			return false;
		}
		EqualsBuilder eb = new EqualsBuilder();
		eb.append(getName(), ((SegmentedTextDocument) obj).getName());
		return eb.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
		hashCodeBuilder.append(getName());
		return super.hashCode();
	}

}
