package fr.vinze.textanalysis.document.impl;

import fr.vinze.textanalysis.document.Token;

/**
 * An implementation for {@link Token} that are neither word nor punctuation.
 * 
 * @author Vinze
 *
 */
public class SpecialToken extends AbstractTokenImpl {

	final TokenType type;
	final String content;

	public SpecialToken(TokenType type) {
		this(type, null);
	}

	public SpecialToken(TokenType type, String content) {
		super();
		this.type = type;
		this.content = content;
	}

	@Override
	public String toString() {
		if (content != null) {
			return content;
		} else {
			return type.toString();
		}
	}

	public static enum TokenType {

		END_OF_PARAGRAPH, EMPTY_LINE, END_OF_DOCUMENT, SEPARATOR, UNKNOWN_CHARSEQUENCE;

		// TODO other special tokens?

	}

}
