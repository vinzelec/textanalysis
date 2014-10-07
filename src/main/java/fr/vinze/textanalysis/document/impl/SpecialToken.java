package fr.vinze.textanalysis.document.impl;

import fr.vinze.textanalysis.document.Token;

/**
 * A few static implementations of {@link Token} that are neither word nor
 * punctuation.
 * 
 * @author Vinze
 *
 */
public class SpecialToken extends AbstractTokenImpl {

	final static SpecialToken END_OF_PARAGRAPH = new SpecialToken();
	final static SpecialToken EMPTY_LINE = new SpecialToken();
	final static SpecialToken END_OF_DOCUMENT = new SpecialToken();
	final static SpecialToken SEPARATOR = new SpecialToken();

	// TODO other special tokens?

	private SpecialToken() {

	}

}
