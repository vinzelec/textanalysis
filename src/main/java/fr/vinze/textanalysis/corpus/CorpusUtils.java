package fr.vinze.textanalysis.corpus;

import fr.vinze.textanalysis.corpus.impl.RawTextDocumentCorpusImpl;
import fr.vinze.textanalysis.corpus.impl.SegmentedTextDocumentCorpusImpl;
import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.mapper.RawTextMapper;
import fr.vinze.textanalysis.mapper.SegmentedTextMapper;
import fr.vinze.textanalysis.segmentation.Splitter;

/**
 * Utils function to use method document->document on all documents of a corpus
 * 
 * @author Vinze
 *
 */
public abstract class CorpusUtils {

	public static RawTextDocumentCorpus map(RawTextDocumentCorpus corpus,
			RawTextMapper mapper) {
		RawTextDocumentCorpus newCorpus = new RawTextDocumentCorpusImpl();
		for (RawTextDocument document : corpus.getDocuments()) {
			RawTextDocument newDocument = mapper.map(document);
			newCorpus.addDocument(newDocument);
		}
		return newCorpus;
	}

	public static SegmentedTextDocumentCorpus map(
			SegmentedTextDocumentCorpus corpus, SegmentedTextMapper mapper) {
		SegmentedTextDocumentCorpus newCorpus = new SegmentedTextDocumentCorpusImpl();
		for (SegmentedTextDocument document : corpus.getDocuments()) {
			SegmentedTextDocument newDocument = mapper.map(document);
			newCorpus.addDocument(newDocument);
		}
		return newCorpus;
	}

	public static SegmentedTextDocumentCorpus split(
			RawTextDocumentCorpus corpus, Splitter splitter) {
		SegmentedTextDocumentCorpus newCorpus = new SegmentedTextDocumentCorpusImpl();
		for (RawTextDocument document : corpus.getDocuments()) {
			SegmentedTextDocument newDocument = splitter.split(document);
			newCorpus.addDocument(newDocument);
		}
		return newCorpus;
	}

}
