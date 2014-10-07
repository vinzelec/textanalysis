package fr.vinze.textanalysis.document;

public interface Punctuation extends Token {

	PunctuationMark getPunctuationMark();

	public static enum PunctuationMark {

		APOSTRPHE('\''),
		BRACKET_OPEN('['), BRACKET_CLOSE(']'),
		ANGLE_BRACKET_OPEN('<'), ANGLE_BRACKET_CLOSE('>'),
		COLON(':'),
		COMMA(','),
		DASH_EM('—'),
		DASH_EN('–'),
		DOT('.'),
		ELLIPSIS('…'),
		PARENTHESIS_OPEN('('), PARENTHESIS_CLOSE(')'),
		QUESTION('?');
		// TODO complete
		
		char token;

		private PunctuationMark(char token) {
			this.token = token;
		}

		@Override
		public String toString() {
			return ""+token;
		}
		
		public PunctuationMark fromChar(char punctuation){
			// TODO
			return null;
		}
	}

}
