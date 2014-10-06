package fr.vinze.textanalysis.mapper;

import fr.vinze.textanalysis.document.SegmentedTextDocument;

/**
 * Interface for all mapper working on {@link SegmentedTextDocument}
 * 
 * @author Vinze
 *
 */
public interface SegmentedTextMapper {

	SegmentedTextDocument map(SegmentedTextDocument document);

}
