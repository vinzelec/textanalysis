package fr.vinze.textanalysis.document.impl;

import fr.vinze.textanalysis.document.Punctuation;
import fr.vinze.textanalysis.document.Token;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PunctuationImpl extends AbstractTokenImpl implements Punctuation {

	private final PunctuationMark mark;

	public PunctuationImpl(Punctuation punctuation) {
		super();
		this.mark = punctuation.getPunctuationMark();
		mergeMetadata(punctuation);
	}

	public PunctuationImpl(PunctuationMark mark) {
		super();
		this.mark = mark;
	}

	@Override
	public PunctuationMark getPunctuationMark() {
		return mark;
	}

	@Override
	public Token clone() {
		return new PunctuationImpl(this);
	}

	/*
	 * Metadatas have no impact on equals and hashcode
	 */

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Punctuation)) {
			return false;
		}
		EqualsBuilder eb = new EqualsBuilder();
		eb.append(getPunctuationMark(), ((Punctuation) obj).getPunctuationMark());
		return eb.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
		hashCodeBuilder.append(getPunctuationMark());
		return super.hashCode();
	}

	@Override
	public String toString() {
		return mark.toString();
	}
}
