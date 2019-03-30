package fr.vinze.textanalysis.parser;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.EnumMap;
import java.util.Map;

public abstract class DocumentParserFactory {

	private DocumentParserFactory() {
	}

	private static Map<DocumentType, DocumentParser> registry;

	private static Map<DocumentType, DocumentParser> getRegistry() {
		// synchronized initialization for the registry singleton
		synchronized (DocumentParserFactory.class) {
			if (registry == null) {
				registry = new EnumMap<>(DocumentType.class);
			}
		}
		return registry;
	}

	/**
	 * Clear the parser registry
	 */
	public static void clear() {
		getRegistry().clear();
	}

	/**
	 * Define a parser to register (it will be associated to all file of type
	 * {@link DocumentParser#canParse()})
	 *
	 * @param parser the parser to register
	 */
	public static void registerParser(DocumentParser parser) {
		getRegistry().put(parser.canParse(), parser);
	}

	public static DocumentParser getParser(DocumentType type) throws DocumentParserNotAvailable {
		DocumentParser parser = getRegistry().get(type);
		if (parser == null) {
			throw new DocumentParserNotAvailable("No parser is available for file type " + type);
		}
		return parser;
	}

	public static DocumentParser getParser(String fileName) throws DocumentParserNotAvailable, DocumentTypeNotSupported {
		String extension = FilenameUtils.getExtension(fileName);
		DocumentType type = DocumentType.fromString(extension);
		return getParser(type);
	}

	public static DocumentParser getParser(File file) throws DocumentParserNotAvailable, DocumentTypeNotSupported {
		return getParser(file.getName());
	}

}
