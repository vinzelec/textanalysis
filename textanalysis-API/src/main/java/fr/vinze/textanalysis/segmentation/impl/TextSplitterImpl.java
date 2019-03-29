package fr.vinze.textanalysis.segmentation.impl;

import fr.vinze.textanalysis.document.Punctuation;
import fr.vinze.textanalysis.document.Punctuation.PunctuationMark;
import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.document.impl.PunctuationImpl;
import fr.vinze.textanalysis.document.impl.SegmentedTextDocumentImpl;
import fr.vinze.textanalysis.document.impl.SpecialToken;
import fr.vinze.textanalysis.document.impl.SpecialToken.TokenType;
import fr.vinze.textanalysis.document.impl.WordImpl;
import fr.vinze.textanalysis.segmentation.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Implementation for {@link Splitter} that only works on
 * {@link RawTextDocument#getContent()} and ignore every markup.
 * </p>
 * <p>
 * It is recommended to use both {@link fr.vinze.textanalysis.mapper.impl.PunctuationCleaner} and
 * {@link fr.vinze.textanalysis.mapper.impl.ReturnCarriageCleaner} prior to using this implementation
 * </p>
 * 
 * @author Vinze
 *
 */
public class TextSplitterImpl implements Splitter {

	private static final Logger log = LoggerFactory.getLogger(TextSplitterImpl.class);

	public static final String END_OF_LINE_REGEX = "\r\n|[\n\r\u0085\u2028\u2029]";
	public static final String SEPARATOR_REGEX = "[\\*-_]+";
	// anything without space not matching word or punctuation
	public static final String UNKNOWN_REGEX = "[^\\s\\p{Punct}]+";
	public static final String SPLIT_REGEX = Word.WORD_REGEX + "|" + Punctuation.PUNCTUATION_REGEX + "|"
			+ UNKNOWN_REGEX;

	// FIXME this regexp is bugged but should be enough for now...
	// if a regexp guru could provide better, I don't think I can...

	@Override
	public SegmentedTextDocument apply(RawTextDocument document) {
		SegmentedTextDocument segmentedDoc = new SegmentedTextDocumentImpl(document);
		String[] lines = document.getContent().split(END_OF_LINE_REGEX);
		log.debug("input text contains {} end", lines.length);
		Pattern separatorPattern = Pattern.compile(SEPARATOR_REGEX);
		Pattern wordOrPunctPattern = Pattern.compile(SPLIT_REGEX);
		Pattern wordPattern = Pattern.compile(Word.WORD_REGEX);
		Pattern punctPattern = Pattern.compile(Punctuation.PUNCTUATION_REGEX);
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
				String token = wordOrPunctMatcher.group();
				// is it a word?
				Matcher matcher = wordPattern.matcher(token);
				if (matcher.matches()) {
					segmentedDoc.getTokens().add(new WordImpl(token));
					continue;
				}
				// is it a punctuation?
				matcher = punctPattern.matcher(token);
				if (matcher.matches()) {
					segmentedDoc.getTokens().add(new PunctuationImpl(PunctuationMark.fromChar(token.charAt(0))));
					continue;
				}
				// otherwise
				segmentedDoc.getTokens().add(new SpecialToken(TokenType.UNKNOWN_CHARSEQUENCE, token));
			}
			segmentedDoc.getTokens().add(new SpecialToken(TokenType.END_OF_PARAGRAPH));
		}
		segmentedDoc.getTokens().add(new SpecialToken(TokenType.END_OF_DOCUMENT));
		return segmentedDoc;
	}

}
