package fr.vinze.textanalysis.corpus;

import java.util.Collection;

import fr.vinze.textanalysis.document.SegmentedTextDocument;

public interface SegmentedTextDocumentCorpus {

	String getId();

	Collection<SegmentedTextDocument> getDocuments();

	SegmentedTextDocument getDocument(String documentName);

	void addDocument(SegmentedTextDocument document);

	int getSize();

}
