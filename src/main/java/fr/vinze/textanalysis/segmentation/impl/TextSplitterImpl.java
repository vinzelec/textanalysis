package fr.vinze.textanalysis.segmentation.impl;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.impl.SegmentedTextDocumentImpl;
import fr.vinze.textanalysis.segmentation.Splitter;

/**
 * Implementation for {@link Splitter} that only works on
 * {@link RawTextDocument#getContent()} and ignore every markup.
 * 
 * @author Vinze
 *
 */
public class TextSplitterImpl implements Splitter {

	public SegmentedTextDocument split(RawTextDocument document) {
		SegmentedTextDocument segmentedDoc = new SegmentedTextDocumentImpl(
				document);

		// TODO split

		return segmentedDoc;
	}

}
