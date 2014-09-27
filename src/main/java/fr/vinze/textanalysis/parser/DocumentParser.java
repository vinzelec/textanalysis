package fr.vinze.textanalysis.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import fr.vinze.textanalysis.document.RawTextDocument;

public interface DocumentParser {

	RawTextDocument parse(File file) throws FileNotFoundException, ParseException, IOException;
	
	DocumentType canParse();
	
}
