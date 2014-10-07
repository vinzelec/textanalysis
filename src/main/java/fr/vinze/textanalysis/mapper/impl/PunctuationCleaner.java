package fr.vinze.textanalysis.mapper.impl;

import fr.vinze.textanalysis.document.Punctuation.PunctuationMark;
import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.mapper.RawTextMapper;

/**
 * Clean the punctuations in the documents... for example three dots are
 * replaced by a {@link PunctuationMark#ELLIPSIS} to help the parsing afterward.
 * 
 * @author Vinze
 *
 */
public class PunctuationCleaner implements RawTextMapper {

	public RawTextDocument map(RawTextDocument document) {
		// TODO ellipsis
		// TODO quotes
		return null;
	}

}
