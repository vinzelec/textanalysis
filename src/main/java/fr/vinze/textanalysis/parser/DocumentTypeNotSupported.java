package fr.vinze.textanalysis.parser;

/**
 * exception thrown by {@link DocumentParserFactory} when requested a type that don't belong to {@link DocumentType}.
 * 
 * @author Vinze
 *
 */
public class DocumentTypeNotSupported extends Exception {

	private static final long serialVersionUID = 844523057138707054L;

	public DocumentTypeNotSupported() {
		super();
	}
	
	public DocumentTypeNotSupported(String message, Throwable cause) {
		super(message, cause);
	}

	public DocumentTypeNotSupported(String message) {
		super(message);
	}

	public DocumentTypeNotSupported(Throwable cause) {
		super(cause);
	}

	
}
