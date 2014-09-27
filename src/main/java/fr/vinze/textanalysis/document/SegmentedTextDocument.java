package fr.vinze.textanalysis.document;

import java.util.List;

public interface SegmentedTextDocument {

	public RawTextDocument getSource();

	public List<Token> getTokens();

}
