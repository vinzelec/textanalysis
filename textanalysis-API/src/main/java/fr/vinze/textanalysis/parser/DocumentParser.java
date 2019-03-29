package fr.vinze.textanalysis.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import fr.vinze.textanalysis.document.RawTextDocument;

/**
 * Document parser: builds a {@link RawTextDocument} reference from a text file or String content.
 *
 * @author Vinze
 */
public interface DocumentParser {

	default RawTextDocument parse(File file) throws ParseException, IOException {
		try (InputStream is = new FileInputStream(file)) {
			String name = FilenameUtils.getName(file.getAbsolutePath());
			String content = IOUtils.toString(is);
			return parse(name, content);
		}
	}
	
	RawTextDocument parse(String name, String content) throws ParseException, IOException;

	DocumentType canParse();
	
}
