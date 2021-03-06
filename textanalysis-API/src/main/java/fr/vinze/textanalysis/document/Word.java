package fr.vinze.textanalysis.document;


public interface Word extends Token {

	// letters (including all accented) and numbers
	public final String WORD_REGEX = "[\\p{L}\\d]+";

	String getWord();
}
