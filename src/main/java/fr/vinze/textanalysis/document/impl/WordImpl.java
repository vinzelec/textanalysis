package fr.vinze.textanalysis.document.impl;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import fr.vinze.textanalysis.document.Word;

public class WordImpl extends AbstractTokenImpl implements Word {

	String word;

	public WordImpl(String word) {
		super();
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	/*
	 * Metadatas have no impact on equals and hashcode
	 */

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Word)) {
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
}
