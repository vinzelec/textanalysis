package fr.vinze.textanalysis.parser.impl;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.parser.DocumentParser;
import fr.vinze.textanalysis.parser.DocumentType;

/**
 * implementation of DocumentParser for *.txt files
 * 
 * @author Vinze
 *
 */
public class TxtDocumentParser implements DocumentParser {

	public DocumentType canParse() {
		return DocumentType.TXT;
	}

	@Override
	public RawTextDocument parse(String name, String content) {
		return new RawTextDocumentImpl(name, content);
	}

}
