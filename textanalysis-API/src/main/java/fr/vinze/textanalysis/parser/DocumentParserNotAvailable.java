package fr.vinze.textanalysis.parser;

/**
 * exception thrown by {@link DocumentParserFactory} when requested a type with no parser registered for.
 * 
 * @author Vinze
 *
 */
public class DocumentParserNotAvailable extends Exception {

	private static final long serialVersionUID = -7528321861938672070L;

	public DocumentParserNotAvailable() {
		super();
	}

	public DocumentParserNotAvailable(String message, Throwable cause) {
		super(message, cause);
	}

	public DocumentParserNotAvailable(String message) {
		super(message);
	}

	public DocumentParserNotAvailable(Throwable cause) {
		super(cause);
	}

	
	
}
