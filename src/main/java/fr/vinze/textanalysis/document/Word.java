package fr.vinze.textanalysis.document;


public interface Word extends Token {

	// letters and numbers
	public final static String WORD_REGEX = "\\w+";

	// FIXME add all accented letters

	String getWord();
}
