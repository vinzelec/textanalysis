package fr.vinze.textanalysis.parser.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.SAXParser;

import org.ccil.cowan.tagsoup.jaxp.SAXParserImpl;
import org.xml.sax.SAXException;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.parser.DocumentType;
import fr.vinze.textanalysis.parser.ParseException;

public class HTMLParser extends XHTMLParser {

	public HTMLParser() {
		super();
	}

	public HTMLParser(boolean extractSource) {
		super(extractSource);
	}

	public DocumentType canParse() {
		return DocumentType.HTML;
	}

	public RawTextDocument parse(File file) throws FileNotFoundException, ParseException, IOException {
		try {
			SAXParser tagsoupParser = SAXParserImpl.newInstance(null);
			return parse(file, tagsoupParser);
		} catch (SAXException e) {
			throw new ParseException(e);
		}
	}

}
