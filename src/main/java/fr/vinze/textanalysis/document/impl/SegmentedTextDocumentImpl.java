package fr.vinze.textanalysis.document.impl;

import java.util.ArrayList;
import java.util.List;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;

public class SegmentedTextDocumentImpl implements SegmentedTextDocument {

	final RawTextDocument source;
	String name;
	List<Token> tokens;
	Integer tokenCount = null;

	public SegmentedTextDocumentImpl(RawTextDocument source) {
		this(source.getName(), source);
	}

	public SegmentedTextDocumentImpl(String name, RawTextDocument source) {
		super();
		this.source = source;
		this.name = name;
		tokens = new ArrayList<Token>();
	}

	public RawTextDocument getSource() {
		return source;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public String getName() {
		return name;
	}

	public void setTokenCount(int count) {
		tokenCount = count;
	}

	public int getTokenCount() {
		if (tokenCount == null) {
			return tokens.size();
		}
		return tokenCount;
	}

}
