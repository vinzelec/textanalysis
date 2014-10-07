package fr.vinze.textanalysis.segmentation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;
import fr.vinze.textanalysis.document.Punctuation;

public class SplitRegexpTest extends TestCase {

	public void testPunctuationRegex() {
		Pattern pattern = Pattern.compile(Punctuation.PUNCTUATION_REGEX);
		Matcher matcherEmpty = pattern.matcher("");
		assertFalse("empty string is not a valid punctuation",
				matcherEmpty.matches());
		Matcher matcherDot = pattern.matcher(".");
		assertTrue("dot is a valid punctuation", matcherDot.matches());
		Matcher matcherTwoPunctuations = pattern.matcher("()");
		assertFalse("() is not a valid punctuation",
				matcherTwoPunctuations.matches());
		Matcher matcherWord = pattern.matcher("toto");
		assertFalse("a word is not a valid punctuation", matcherWord.matches());
		Matcher matcherChar = pattern.matcher("T");
		assertFalse("a char is not a valid punctuation", matcherChar.matches());

	}

}
