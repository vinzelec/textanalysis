package fr.vinze.textanalysis.segmentation.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.vinze.textanalysis.document.Punctuation;
import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.document.impl.SegmentedTextDocumentImpl;
import fr.vinze.textanalysis.document.impl.SpecialToken;
import fr.vinze.textanalysis.document.impl.SpecialToken.TokenType;
import fr.vinze.textanalysis.segmentation.Splitter;

/**
 * Implementation for {@link Splitter} that only works on
 * {@link RawTextDocument#getContent()} and ignore every markup.
 * 
 * @author Vinze
 *
 */
public class TextSplitterImpl implements Splitter {

	private static final Logger log = LoggerFactory.getLogger(TextSplitterImpl.class);

	public static final String END_OF_LINE_REGEX = "[\n\r\u0085\u2028\u2029]";
	public static final String SEPARATOR_REGEX = "[\\*-_]+";
	public static final String WORD_OR_PUNCTUATION_REGEX = Word.WORD_REGEX + "|" + Punctuation.PUNCTUATION_REGEX;

	public SegmentedTextDocument split(RawTextDocument document) {
		SegmentedTextDocument segmentedDoc = new SegmentedTextDocumentImpl(document);
		String[] lines = document.getContent().split(END_OF_LINE_REGEX);
		log.debug("input text contains " + lines.length + " end");
		Pattern separatorPattern = Pattern.compile(SEPARATOR_REGEX);
		Pattern wordOrPunctPattern = Pattern.compile(WORD_OR_PUNCTUATION_REGEX);
		for (String line : lines) {
			String trimed = line.trim();
			if ("".equals(trimed)) {
				segmentedDoc.getTokens().add(new SpecialToken(TokenType.EMPTY_LINE));
				continue;
			}
			Matcher separatorMatcher = separatorPattern.matcher(trimed);
			if (separatorMatcher.matches()) {
				segmentedDoc.getTokens().add(new SpecialToken(TokenType.SEPARATOR, trimed));
				continue;
			}
			Matcher wordOrPunctMatcher = wordOrPunctPattern.matcher(trimed);
			while (wordOrPunctMatcher.find()) {
				// TODO distinguish words and punctuation
			}
			segmentedDoc.getTokens().add(new SpecialToken(TokenType.END_OF_PARAGRAPH));
		}
		segmentedDoc.getTokens().add(new SpecialToken(TokenType.END_OF_DOCUMENT));
		return segmentedDoc;
	}

}
