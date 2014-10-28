package fr.vinze.textanalysis.segmentation.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.document.impl.SegmentedTextDocumentImpl;
import fr.vinze.textanalysis.document.impl.WordImpl;
import fr.vinze.textanalysis.mapper.impl.KeepOnlyWords;
import fr.vinze.textanalysis.segmentation.Splitter;

/**
 * <p>
 * Implementation for {@link Splitter} that only works on {@link RawTextDocument#getContent()} and ignore every markup.
 * </p>
 * <p>
 * This only extracts the {@link Word} from the texts.
 * </p>
 * <p>
 * Result should be the same as applying {@link KeepOnlyWords} mapper after using a {@link TextSplitterImpl}
 * </p>
 * 
 * @author Vinze
 *
 */
public class WordOnlySplitterImpl implements Splitter {

	public SegmentedTextDocument split(RawTextDocument document) {
		SegmentedTextDocument segmentedDoc = new SegmentedTextDocumentImpl(document);
		Pattern wordPattern = Pattern.compile(Word.WORD_REGEX);
		Matcher wordMatcher = wordPattern.matcher(document.getContent());
		while (wordMatcher.find()) {
			String word = wordMatcher.group();
			segmentedDoc.getTokens().add(new WordImpl(word));
		}
		return segmentedDoc;
	}

}
