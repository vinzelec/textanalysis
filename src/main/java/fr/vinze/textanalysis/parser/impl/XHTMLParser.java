package fr.vinze.textanalysis.parser.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.IOUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.parser.DocumentParser;
import fr.vinze.textanalysis.parser.DocumentType;
import fr.vinze.textanalysis.parser.ParseException;
import fr.vinze.utils.RecordingFilterInputStream;

public class XHTMLParser implements DocumentParser {

	final boolean extractSource;

	public XHTMLParser() {
		this(true);
	}

	/**
	 * 
	 * @param extractSource
	 *            should the all content (with tags) included in {@link RawTextDocument#getRawSource()}? (default to
	 *            <code>true</code>, <code>false</code> is recommended for large documents if tags won't be used
	 *            in any further mappers)
	 */
	public XHTMLParser(boolean extractSource) {
		super();
		this.extractSource = extractSource;
	}

	public DocumentType canParse() {
		return DocumentType.XHTML;
	}

	public RawTextDocument parse(File file) throws FileNotFoundException, ParseException, IOException {
		try {
			return parse(file, SAXParserFactory.newInstance().newSAXParser());
		} catch (ParserConfigurationException e) {
			throw new ParseException(e);
		} catch (SAXException e) {
			throw new ParseException(e);
		}
	}

	protected RawTextDocument parse(File file, SAXParser parser) throws FileNotFoundException, ParseException,
			IOException {
		InputStream input = null;
		try {
			input = new FileInputStream(file);
			if (extractSource) {
				input = new RecordingFilterInputStream(input);
			}
			XHTMLHandler handler = new XHTMLHandler();
			parser.parse(input, handler);
			RawTextDocument doc;
			if (input instanceof RecordingFilterInputStream) {
				doc = new RawTextDocumentImpl("", handler.getContent(),
						((RecordingFilterInputStream) input).getRecorded());
			} else {
				doc = new RawTextDocumentImpl("", handler.getContent());
			}
			return doc;
		} catch (SAXException e) {
			throw new ParseException(e);
		} finally {
			if (input != null) {
				IOUtils.closeQuietly(input);
			}
		}
	}

	private static class XHTMLHandler extends DefaultHandler {

		StringBuilder builder;

		public XHTMLHandler() {
			builder = new StringBuilder();
		}

		public String getContent() {
			return builder.toString();
		}

		boolean inBody = false;

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			// at body start recording
			if ("body".equals(localName)) {
				inBody = true;
			}
			// new line
			if ("br".equals(localName)) {
				builder.append('\n');
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			// end of paragraph
			if ("p".equals(localName)) {
				builder.append('\n');
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			// text content
			if (inBody) {
				builder.append(ch);
			}
		}

	}

}
