package fr.vinze.textanalysis.document;

import java.util.List;

public interface SegmentedTextDocument {

	RawTextDocument getSource();

	List<Token> getTokens();

	String getName();

}
