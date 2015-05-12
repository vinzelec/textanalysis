package fr.vinze.textanalysis.parser;

public enum CorpusType {

	ZIP, RAR, TAR, DIR, EPUB;

	public static CorpusType fromString(String extension) throws DocumentTypeNotSupported {
		if ("".equals(extension)) return DIR;
		if("ZIP".equalsIgnoreCase(extension)) return ZIP;
		if("RAR".equalsIgnoreCase(extension)) return RAR;
		if("TAR".equalsIgnoreCase(extension)) return TAR;
		if("EPUB".equalsIgnoreCase(extension)) return EPUB;
		throw new DocumentTypeNotSupported("extension "+extension+" is not supported");
	}

	public static String toString(CorpusType type) {
		switch (type) {
		case ZIP:
			return "zip";
		case RAR:
			return "rar";
		case EPUB:
			return "epub";
		case TAR:
			return "tar";
		default:
			return null;
		}
	}

}
