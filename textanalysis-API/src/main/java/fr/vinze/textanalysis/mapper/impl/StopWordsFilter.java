package fr.vinze.textanalysis.mapper.impl;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.document.impl.SegmentedTextDocumentImpl;
import fr.vinze.textanalysis.mapper.SegmentedTextMapper;
import fr.vinze.textanalysis.resources.StopWords;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class StopWordsFilter implements SegmentedTextMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(StopWordsFilter.class);

	private Set<String> words;

	public StopWordsFilter(File... files) {
		words = new HashSet<>();
		for (File file : files) {
			processFile(file);
		}
	}

	public StopWordsFilter(Collection<File> files) {
		words = new HashSet<>();
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
		try (Reader reader = new FileReader(swFile)) {
			words.addAll(IOUtils.readLines(reader));
		} catch (FileNotFoundException e) {
			LOGGER.warn("file {} was not found, no word has been added to the stop words list", swFile, e);
		} catch (IOException e) {
			LOGGER.warn("error while reading {} was not found, no word has been added to the stop words list",
					swFile, e);
		}
	}

	@Override
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
