package fr.vinze.textanalysis.resources;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Locale;

/**
 * <p>
 * Singleton class to access available stop-words resources files for several languages.
 * </p>
 * <p>
 * The singleton is initialized with the files from <a href="https://code.google.com/p/stop-words">stop words
 * project</a> containing 29 languages. Additional files can be added using {@link #addFile(File, String)}
 * </p>
 * <p>
 * The stop-words files have the simple structure of a word per line. The extension must be .txt and last two characters
 * of the name are the language code (ie whatever-name_en.txt for english).
 * </p>
 * 
 * @author Vinze
 *
 */
public class StopWords {

	private static final Logger LOGGER = LoggerFactory.getLogger(StopWords.class);

	private static StopWords instance = null;

	public static StopWords getInstance() {
		synchronized (StopWords.class) {
			if (instance == null) {
				instance = new StopWords();
			}
			return instance;
		}
	}

	private Multimap<String, File> files;

	private StopWords() {
		files = ArrayListMultimap.create();
		URL stopWordsDirectoryUrl = getClass().getResource("/stop-words");
		File stopWordsDirectory = new File(stopWordsDirectoryUrl.getPath());
		if (stopWordsDirectory.exists()) {
			for(File swfile : stopWordsDirectory.listFiles()) {
				String filename = swfile.getName();
				String lang = filename.substring(filename.length() - 6, filename.length() - 4);
				addFile(swfile, lang);
			}
		} else {
			LOGGER.warn("missing directory /stop-words, no stop words available at startup");
		}
	}

	public void addFile(File file, String lang) {
		LOGGER.debug("associating stop word file {} for lang {}", file.getName(), lang);
		files.put(lang.toUpperCase(), file);
	}

	public void addFile(File file, Locale locale) {
		addFile(file, locale.getLanguage());
	}

	public Collection<File> getStopWords(String lang) {
		return files.get(lang.toUpperCase());
	}

	public Collection<File> getStopWords(Locale locale) {
		return getStopWords(locale.getLanguage());
	}
	
	// TODO other methods?

}
