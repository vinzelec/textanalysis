package fr.vinze.textanalysis.corpus;

import java.util.Collection;

import fr.vinze.textanalysis.document.RawTextDocument;

public interface RawTextDocumentCorpus {

	String getId();

	Collection<RawTextDocument> getDocuments();

	RawTextDocument getDocument(String documentName);

	void addDocument(RawTextDocument document);

	int getSize();

}
