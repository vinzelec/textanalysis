package fr.vinze.textanalysis.mapper;

import fr.vinze.textanalysis.document.RawTextDocument;

import java.util.function.Function;

/**
 * Interface for all mapper working on {@link RawTextDocument}
 * 
 * @author Vinze
 *
 */
public interface RawTextMapper extends Function<RawTextDocument,RawTextDocument> {

}
