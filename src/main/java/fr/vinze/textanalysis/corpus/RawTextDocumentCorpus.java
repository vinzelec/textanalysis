package fr.vinze.textanalysis.corpus;

import java.util.Collection;

import fr.vinze.textanalysis.document.RawTextDocument;

public interface RawTextDocumentCorpus {

	Collection<RawTextDocument> getDocuments();

	void addDocument(RawTextDocument document);

}
