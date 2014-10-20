package fr.vinze.textanalysis.mapper.impl;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.mapper.SegmentedTextMapper;
import fr.vinze.textanalysis.resources.StopWords;

public class StopWordsFilter implements SegmentedTextMapper {

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
		// TODO
	}

	public SegmentedTextDocument map(SegmentedTextDocument document) {
		// TODO Auto-generated method stub
		return null;
	}

}
