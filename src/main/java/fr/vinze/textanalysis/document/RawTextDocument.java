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

	/**
	 * Two instance with same {@link #getUniqueID()} represents the same actual document.
	 * 
	 * @return an ID identifying the document within the application.
	 */
	String getUniqueID();
		
}
