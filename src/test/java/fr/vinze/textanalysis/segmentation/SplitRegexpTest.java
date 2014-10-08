package fr.vinze.textanalysis.segmentation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;
import fr.vinze.textanalysis.document.Punctuation;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.segmentation.impl.TextSplitterImpl;

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

	public void testWordRegex() {
		Pattern pattern = Pattern.compile(Word.WORD_REGEX);
		Matcher matcherEmpty = pattern.matcher("");
		assertFalse("empty string is not a valid word", matcherEmpty.matches());
		Matcher matcherDot = pattern.matcher(".");
		assertFalse("dot is not a valid word", matcherDot.matches());
		Matcher matcherTwoWords = pattern.matcher("test toto");
		assertFalse("two words is not a valid word", matcherTwoWords.matches());
		Matcher matcherWord = pattern.matcher("toto");
		assertTrue("one word is a valid word", matcherWord.matches());
		Matcher matcherChar = pattern.matcher("T");
		assertTrue("a char is a valid word", matcherChar.matches());
		Matcher matcherWithDigits = pattern.matcher("h2o");
		assertTrue("a word with digit is a valid word", matcherWithDigits.matches());
		Matcher matcherWithAccents = pattern.matcher("yòdâ");
		assertTrue("a word with accents is a valid word", matcherWithAccents.matches());
	}

	public void testSplitRegex() {
		Pattern pattern = Pattern.compile(TextSplitterImpl.SPLIT_REGEX);
		Matcher matcherEmpty = pattern.matcher("");
		assertFalse("empty string is not a valid word", matcherEmpty.matches());
		Matcher matcherSentence = pattern.matcher("This, a sentence µr.");
		assertTrue("This sentence contains a first element", matcherSentence.find());
		assertEquals("First element is word 'This'", "This", matcherSentence.group());
		assertTrue("This sentence contains a second element", matcherSentence.find());
		assertEquals("Second element is comma", ",", matcherSentence.group());
		assertTrue("This sentence contains a 3rd element", matcherSentence.find());
		assertEquals("3rd element is word a", "a", matcherSentence.group());
		assertTrue("This sentence contains a 4th element", matcherSentence.find());
		assertEquals("4th element is word 'sentence'", "sentence", matcherSentence.group());
		assertTrue("This sentence contains a 5th element", matcherSentence.find());
		assertEquals("5th element is aµr$", "µr", matcherSentence.group());
		assertTrue("This sentence contains a 6th element", matcherSentence.find());
		assertEquals("6th element is dot", ".", matcherSentence.group());
		assertFalse("This sentence donesn't contain a 7th element", matcherSentence.find());
	}

}
