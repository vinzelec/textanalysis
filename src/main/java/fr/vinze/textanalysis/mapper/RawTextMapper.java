package fr.vinze.textanalysis.mapper;

import fr.vinze.textanalysis.document.RawTextDocument;

/**
 * Interface for all mapper working on {@link RawTextDocument}
 * 
 * @author Vinze
 *
 */
public interface RawTextMapper {

	RawTextDocument map(RawTextDocument document);

}
