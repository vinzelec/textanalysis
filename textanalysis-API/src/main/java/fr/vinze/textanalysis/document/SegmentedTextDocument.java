package fr.vinze.textanalysis.document;

import java.util.List;

public interface SegmentedTextDocument {

	String getId();

	RawTextDocument getSource();

	List<Token> getTokens();

	String getName();

	/**
	 * @return the number of tokens (that can differ from {@link #getTokens()} size)
	 */
	int getTokenCount();

	void setTokenCount(int count);

	/**
	 * Two instance with same {@link #getUniqueID()} represents the same actual document.
	 * 
	 * @return an ID identifying the document within the application.
	 */
	String getUniqueID();

}
