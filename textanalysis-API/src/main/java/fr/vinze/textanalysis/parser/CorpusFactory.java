package fr.vinze.textanalysis.parser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

public abstract class CorpusFactory {

	private static Map<CorpusType, CorpusParser> registry;

	private static Map<CorpusType, CorpusParser> getRegistry() {
		// synchronized initialization for the registry singleton
		synchronized (CorpusFactory.class) {
			if (registry == null) {
				registry = new HashMap<CorpusType, CorpusParser>();
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
	 * @param parser
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
