package fr.vinze.textanalysis.document.impl;

import fr.vinze.textanalysis.document.Punctuation;

public class PunctuationImpl extends AbstractTokenImpl implements Punctuation {

	final PunctuationMark mark;

	public PunctuationImpl(PunctuationMark mark) {
		super();
		this.mark = mark;
	}

	public PunctuationMark getPunctuationMark() {
		return mark;
	}
}
