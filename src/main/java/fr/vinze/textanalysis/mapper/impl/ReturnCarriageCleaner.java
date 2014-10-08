package fr.vinze.textanalysis.mapper.impl;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.mapper.RawTextMapper;

/**
 * Replace all <pre>\r\n</pre> by <pre>\n</pre> to prevent split 
 * method to consider two line return.
 * 
 * @author Vinze
 *
 */
public class ReturnCarriageCleaner implements RawTextMapper {

	public RawTextDocument map(RawTextDocument document) {
		String newContent = document.getContent();
		newContent = newContent.replaceAll("\r\n", "\n");
		return new RawTextDocumentImpl(document.getName(), newContent);
	}

}
