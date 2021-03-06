package fr.vinze.textanalysis.document;

public interface Punctuation extends Token {

	final String PUNCTUATIONS = "\\p{Punct}";
	public final String PUNCTUATION_REGEX = "[" + PUNCTUATIONS + "]";
	public final String NO_PUNCTUATION_REGEX = "[^" + PUNCTUATIONS + "]";
	
	PunctuationMark getPunctuationMark();

	public enum PunctuationMark {

		APOSTROPHE('\''),
		BRACKET_OPEN('['), BRACKET_CLOSE(']'),
		ANGLE_BRACKET_OPEN('<'), ANGLE_BRACKET_CLOSE('>'),
		BRACES_OPEN('{'), BRACES_CLOSE('}'),
		COLON(':'),
		COMMA(','),
		DASH_EM('—'), DASH_EN('–'),
		DOT('.'),
		ELLIPSIS('…'),
		EXCLAMATION('!'),
		HYPHEN('-'),
		PARENTHESIS_OPEN('('), PARENTHESIS_CLOSE(')'),
		QUESTION('?'),
		QUOTE('"'), // IMPROVE differentiate all kind of quotes?
		SEMICOLON(';'),
		SLASH('/'), BACKSLASH('\\');
		// TODO complete? (inverted punctuation for Spanish, other typographic marks http://en.wikipedia.org/wiki/Punctuation)
		// IMPROVE all characters in \\p{Punct} regexp (#,$)
		
		char token;

		private PunctuationMark(char token) {
			this.token = token;
		}

		@Override
		public String toString() {
			return ""+token;
		}
		
		public static PunctuationMark fromChar(char punctuation){
			switch (punctuation) {
			case '\'':
				return APOSTROPHE;
			case '[':
				return BRACKET_OPEN;
			case ']':
				return BRACKET_CLOSE;
			case '<':
				return ANGLE_BRACKET_OPEN;
			case '>':
				return ANGLE_BRACKET_CLOSE;
			case '{':
				return BRACES_OPEN;
			case '}':
				return BRACES_CLOSE;
			case ':':
				return COLON;
			case ',':
				return COMMA;
			case '—':
				return DASH_EM;
			case '–':
				return DASH_EN;
			case '.':
				return DOT;
			case '…':
				return ELLIPSIS;
			case '!':
				return EXCLAMATION;
			case '-':
				return HYPHEN;
			case '(':
				return PARENTHESIS_OPEN;
			case ')':
				return PARENTHESIS_CLOSE;
			case '?':
				return QUESTION;
			case '"':
			case '«':
			case '»':
			case '“':
			case '”':
				return QUOTE;
			case ';':
				return SEMICOLON;
			case '/':
				return SLASH;
			case '\\':
				return BACKSLASH;

			default:
				return null;
			}
		}
	}

}
