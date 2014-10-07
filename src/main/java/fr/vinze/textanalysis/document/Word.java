package fr.vinze.textanalysis.document;


public interface Word extends Token {

	// letters and numbers
	public final static String WORD_REGEX = "[a-zA-Z0-9]+";

	String getWord();
}
