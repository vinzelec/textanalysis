package fr.vinze.textanalysis.segmentation;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.SegmentedTextDocument;

import java.util.function.Function;

/**
 * Function to split a raw document into a segmented one.
 */
public interface Splitter extends Function<RawTextDocument, SegmentedTextDocument> {

}
