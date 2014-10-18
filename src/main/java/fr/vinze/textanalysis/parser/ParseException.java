package fr.vinze.textanalysis.parser;


public class ParseException extends Exception {

	private static final long serialVersionUID = 5371250220762399208L;

	public ParseException() {
		super();
	}

	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParseException(String message) {
		super(message);
	}

	public ParseException(Throwable cause) {
		super(cause);
	}

}
