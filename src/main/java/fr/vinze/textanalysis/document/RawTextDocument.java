package fr.vinze.textanalysis.document;

public interface RawTextDocument {

	String getName();

	/**
	 * @return the content of the source document (with tags for markup file for
	 *         example)
	 */
	String getRawSource();

	/**
	 * @return the content as raw text (and only the text)
	 */
	String getContent();
		
}
