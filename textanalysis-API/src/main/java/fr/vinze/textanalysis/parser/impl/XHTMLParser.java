package fr.vinze.textanalysis.parser.impl;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.parser.DocumentParser;
import fr.vinze.textanalysis.parser.DocumentType;
import fr.vinze.textanalysis.parser.ParseException;
import fr.vinze.utils.RecordingFilterInputStream;
import org.apache.commons.io.IOUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

public class XHTMLParser implements DocumentParser {

	private final boolean extractSource;

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

	@Override
	public DocumentType canParse() {
		return DocumentType.XHTML;
	}

	@Override
	public RawTextDocument parse(String name, String content) throws ParseException, IOException {
		// IMPROVE find a way to refactor and not use a temp file
		File temp = File.createTempFile(name, "");
		if (!temp.exists()) {
			throw new IOException("failed to create temporary file for parsing");
		}
		IOUtils.write(content, new FileOutputStream(temp));
		RawTextDocument doc = parse(temp);
		doc.setName(name); // restore original name
		return doc;
	}

	@Override
	public RawTextDocument parse(File file) throws ParseException, IOException {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();

			return parse(file, factory.newSAXParser());
		} catch (ParserConfigurationException | SAXException e) {
			throw new ParseException(e);
		}
	}

	protected RawTextDocument parse(File file, SAXParser parser) throws ParseException, IOException {
		InputStream input = null;
		try {
			input = new FileInputStream(file);
			if (extractSource) {
				input = new RecordingFilterInputStream(input);
			}
			XHTMLHandler handler = new XHTMLHandler();
			extraconfigParser(parser);
			parser.parse(input, handler);
			RawTextDocument doc;
			if (input instanceof RecordingFilterInputStream) {
				doc = new RawTextDocumentImpl(file.getName(), handler.getContent(),
						((RecordingFilterInputStream) input).getRecorded());
			} else {
				doc = new RawTextDocumentImpl(file.getName(), handler.getContent());
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

	protected void extraconfigParser(SAXParser parser) throws SAXException {
		parser.getXMLReader().setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
	}

	private static class XHTMLHandler extends DefaultHandler {

		private StringBuilder builder;

		XHTMLHandler() {
			builder = new StringBuilder();
		}

		public String getContent() {
			return builder.toString();
		}

		boolean inBody = false;

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) {
			// at body start recording
			if ("body".equals(qName)) {
				inBody = true;
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) {
			// new line
			if ("br".equals(qName)) {
				builder.append('\n');
			}
			// end of paragraph
			if ("p".equals(qName)) {
				builder.append('\n');
			}
			// hgroup tags
			if (qName.matches("h\\d")) {
				builder.append('\n');
			}
			// IMPROVE all block element end should append a new line
		}

		@Override
		public void characters(char[] ch, int start, int length) {
			// text content
			if (inBody) {
				String extract = new String(ch, start, length);
				extract = extract.replaceAll("\r\n|[\n\r\u0085\u2028\u2029]", " ");
				extract = extract.replaceAll("\\s+", " ");
				String trimmed = extract.trim();
				if (trimmed.length() == 0) {
					return;
				}
				builder.append(extract);
			}
		}

	}

}
