package fr.vinze.textanalysis.mapper;

import fr.vinze.textanalysis.document.SegmentedTextDocument;

import java.util.function.Function;

/**
 * <p>
 * Interface for all mapper working on {@link SegmentedTextDocument}
 * </p>
 * <p>
 * Implementation should always clone the tokens from input document to output
 * document so that any document is immutable.
 * </p>
 * 
 * @author Vinze
 *
 */
public interface SegmentedTextMapper extends Function<SegmentedTextDocument,SegmentedTextDocument> {

}
