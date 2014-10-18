package fr.vinze.textanalysis.parser.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.parser.DocumentParser;
import fr.vinze.textanalysis.parser.DocumentType;
import fr.vinze.textanalysis.parser.ParseException;

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
			String name = FilenameUtils.getName(file.getAbsolutePath());
			String content = IOUtils.toString(is);
			return new RawTextDocumentImpl(name, content);
		} finally {
			if(is != null) {
				IOUtils.closeQuietly(is);
			}
		}
	}

}
