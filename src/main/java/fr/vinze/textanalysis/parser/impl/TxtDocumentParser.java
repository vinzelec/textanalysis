package fr.vinze.textanalysis.parser.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import org.apache.commons.io.IOUtils;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.model.impl.RawTextDocumentImpl;
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

	public RawTextDocument parse(File file) throws FileNotFoundException,
			ParseException, IOException {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			String content = IOUtils.toString(is);
			return new RawTextDocumentImpl(content);
		} finally {
			if(is != null) {
				IOUtils.closeQuietly(is);
			}
		}
	}

}
