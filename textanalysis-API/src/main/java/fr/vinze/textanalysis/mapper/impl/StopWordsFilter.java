package fr.vinze.textanalysis.mapper.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.document.impl.SegmentedTextDocumentImpl;
import fr.vinze.textanalysis.mapper.SegmentedTextMapper;
import fr.vinze.textanalysis.resources.StopWords;

public class StopWordsFilter implements SegmentedTextMapper {

	private static final Logger log = LoggerFactory.getLogger(StopWordsFilter.class);

	Set<String> words;

	public StopWordsFilter(File... files) {
		words = new HashSet<String>();
		for (File file : files) {
			processFile(file);
		}
	}

	public StopWordsFilter(Collection<File> files) {
		words = new HashSet<String>();
		for (File file : files) {
			processFile(file);
		}
	}

	public StopWordsFilter(String lang) {
		this(StopWords.getInstance().getStopWords(lang));
	}

	public StopWordsFilter(Locale locale) {
		this(locale.getLanguage());
	}

	public StopWordsFilter(Set<String> words) {
		this.words = words;
	}

	private void processFile(File swFile) {
		Reader reader = null;
		try {
			reader = new FileReader(swFile);
			words.addAll(IOUtils.readLines(reader));
		} catch (FileNotFoundException e) {
			log.warn("file " + swFile + " was not found, no word has been added to the stop words list", e);
		} catch (IOException e) {
			log.warn("error while reading " + swFile + " was not found, no word has been added to the stop words list",
					e);
		} finally {
			if (reader != null) {
				IOUtils.closeQuietly(reader);
			}
		}
	}

	public SegmentedTextDocument apply(SegmentedTextDocument document) {
		SegmentedTextDocument outputDoc = new SegmentedTextDocumentImpl(document.getName(), document.getSource());
		for (Token token : document.getTokens()) {
			// only filters words in the stop words list
			if (!(token instanceof Word) || !words.contains(((Word) token).getWord())) {
				outputDoc.getTokens().add(token.clone());
			}
		}
		return outputDoc;
	}

}
