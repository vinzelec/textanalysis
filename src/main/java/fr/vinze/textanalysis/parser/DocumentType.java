package fr.vinze.textanalysis.parser;

public enum DocumentType {

	TXT, RTF, ODT, DOC, DOCX, XHTML, HTML, EPUB;
	
	
	public static DocumentType fromString(String extension) throws DocumentTypeNotSupported {
		if("TXT".equalsIgnoreCase(extension)) return TXT;
		if("TEXT".equalsIgnoreCase(extension)) return TXT;
		if("RTF".equalsIgnoreCase(extension)) return RTF;
		if("ODT".equalsIgnoreCase(extension)) return ODT;
		if("DOC".equalsIgnoreCase(extension)) return DOC;
		if("DOCX".equalsIgnoreCase(extension)) return DOCX;
		if("XHTML".equalsIgnoreCase(extension)) return XHTML;
		if("HTML".equalsIgnoreCase(extension)) return HTML;
		if("HTM".equalsIgnoreCase(extension)) return HTML;
		if("EPUB".equalsIgnoreCase(extension)) return EPUB;
		throw new DocumentTypeNotSupported("extension "+extension+" is not supported");
	}
	
	public static String toString(DocumentType type) {
		switch(type) {
		case DOC: return "doc";
		case DOCX: return "docx";
		case EPUB: return "epub";
		case HTML: return "html";
		case ODT: return "odt";
		case RTF: return "rtf";
		case TXT: return "txt";
		case XHTML: return "xhtml";
		default: return null;
		}
	}
	
}
