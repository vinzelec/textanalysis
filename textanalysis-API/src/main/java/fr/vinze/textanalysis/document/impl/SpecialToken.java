package fr.vinze.textanalysis.document.impl;

import fr.vinze.textanalysis.document.Token;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * An implementation for {@link Token} that are neither word nor punctuation.
 * 
 * @author Vinze
 *
 */
public class SpecialToken extends AbstractTokenImpl {

	private final TokenType type;
	private final String content;

	/**
	 * Creates a new instance by copy
	 *
	 * @param token the token to copy
	 */
	public SpecialToken(SpecialToken token) {
		this(token.getType(), token.getContent());
		mergeMetadata(token);
	}

	public SpecialToken(TokenType type) {
		this(type, null);
	}

	public SpecialToken(TokenType type, String content) {
		super();
		this.type = type;
		this.content = content;
	}

	public TokenType getType() {
		return type;
	}

	public String getContent() {
		return content;
	}

	@Override
	public String toString() {
		if (content != null) {
			return content;
		}
		return type.toString();
	}

	@Override
	public Token clone() {
		return new SpecialToken(this);
	}

	/*
	 * Metadatas have no impact on equals and hashcode
	 */

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SpecialToken)) {
			return false;
		}
		EqualsBuilder eb = new EqualsBuilder();
		eb.append(getType(), ((SpecialToken) obj).getType());
		eb.append(content, ((SpecialToken) obj).content);
		return eb.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
		hashCodeBuilder.append(getType());
		hashCodeBuilder.append(content);
		return super.hashCode();
	}

	public static enum TokenType {

		END_OF_PARAGRAPH, EMPTY_LINE, END_OF_DOCUMENT, SEPARATOR, UNKNOWN_CHARSEQUENCE;

		// TODO other special tokens?

	}

}
