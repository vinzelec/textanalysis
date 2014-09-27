package fr.vinze.textanalysis.segmentation;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.SegmentedTextDocument;

public interface Splitter {

	public SegmentedTextDocument split(RawTextDocument document);
	
}
