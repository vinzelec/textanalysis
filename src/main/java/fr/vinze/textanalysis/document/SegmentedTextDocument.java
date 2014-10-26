package fr.vinze.textanalysis.document;

import java.util.List;

public interface SegmentedTextDocument {

	RawTextDocument getSource();

	List<Token> getTokens();

	String getName();

	/**
	 * @return the number of tokens (that can differ from {@link #getTokens()} size)
	 */
	int getTokenCount();

	void setTokenCount(int count);

}
