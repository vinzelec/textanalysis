package fr.vinze.textanalysis.document.impl;

import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.Word;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class WordImpl extends AbstractTokenImpl implements Word {

	String word;

	/**
	 * Creates a new instance by copy
	 * 
	 * @param word
	 */
	public WordImpl(Word word) {
		super();
		this.word = word.getWord();
		mergeMetadata(word);
	}

	public WordImpl(String word) {
		super();
		this.word = word;
	}

	@Override
	public String getWord() {
		return word;
	}

	@Override
	public Token clone() {
		return new WordImpl(this);
	}

	/*
	 * Metadatas have no impact on equals and hashcode
	 */

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Word)) {
			return false;
		}
		EqualsBuilder eb = new EqualsBuilder();
		eb.append(getWord(), ((Word) obj).getWord());
		return eb.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
		hashCodeBuilder.append(getWord());
		return super.hashCode();
	}

	@Override
	public String toString() {
		return word;
	}
}
