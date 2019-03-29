package fr.vinze.textanalysis.parser.impl;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.parser.DocumentType;
import fr.vinze.textanalysis.parser.ParseException;
import org.ccil.cowan.tagsoup.jaxp.SAXParserImpl;
import org.xml.sax.SAXException;

import javax.xml.parsers.SAXParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HTMLParser extends XHTMLParser {

	public HTMLParser() {
		super();
	}

	public HTMLParser(boolean extractSource) {
		super(extractSource);
	}

	@Override
	public DocumentType canParse() {
		return DocumentType.HTML;
	}

	@Override
	public RawTextDocument parse(File file) throws FileNotFoundException, ParseException, IOException {
		try {
			SAXParser tagsoupParser = SAXParserImpl.newInstance(null);
			return parse(file, tagsoupParser);
		} catch (SAXException e) {
			throw new ParseException(e);
		}
	}

	@Override
	protected void extraconfigParser(SAXParser parser) {
		// do nothing
	}

}
