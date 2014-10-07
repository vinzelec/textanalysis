package fr.vinze.textanalysis.document.impl;

import fr.vinze.textanalysis.document.Word;

public class WordImpl extends AbstractTokenImpl implements Word {

	final String word;

	public WordImpl(String word) {
		super();
		this.word = word;
	}

	public String getWord() {
		return word;
	}

}
