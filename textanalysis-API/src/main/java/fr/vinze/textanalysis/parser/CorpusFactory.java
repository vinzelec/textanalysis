package fr.vinze.textanalysis.parser;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.EnumMap;
import java.util.Map;

public abstract class CorpusFactory {

	private CorpusFactory() {
	}

	private static Map<CorpusType, CorpusParser> registry;

	private static Map<CorpusType, CorpusParser> getRegistry() {
		// synchronized initialization for the registry singleton
		synchronized (CorpusFactory.class) {
			if (registry == null) {
				registry = new EnumMap<>(CorpusType.class);
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
	 * {@link CorpusParser#canParse()})
	 *
	 * @param parser the corpus parser to register
	 */
	public static void registerParser(CorpusParser parser) {
		getRegistry().put(parser.canParse(), parser);
	}

	public static CorpusParser getParser(CorpusType type) throws DocumentParserNotAvailable {
		CorpusParser parser = getRegistry().get(type);
		if (parser == null) {
			throw new DocumentParserNotAvailable("No parser is available for file type " + type);
		}
		return parser;
	}

	public static CorpusParser getParser(File file)
			throws DocumentParserNotAvailable, DocumentTypeNotSupported {
		String extension = FilenameUtils.getExtension(file.getName());
		CorpusType type = CorpusType.fromString(extension);
		return getParser(type);
	}
}
