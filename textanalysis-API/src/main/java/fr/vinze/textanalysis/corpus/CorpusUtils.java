package fr.vinze.textanalysis.corpus;

import fr.vinze.textanalysis.corpus.impl.RawTextDocumentCorpusImpl;
import fr.vinze.textanalysis.corpus.impl.SegmentedTextDocumentCorpusImpl;
import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.mapper.RawTextMapper;
import fr.vinze.textanalysis.mapper.SegmentedTextMapper;
import fr.vinze.textanalysis.segmentation.Splitter;

import java.util.Arrays;
import java.util.List;

/**
 * Util functions to use method document->document on all documents of a corpus
 * 
 * @author Vinze
 *
 */
public interface CorpusUtils {

	static RawTextDocumentCorpus createCorpus(List<RawTextDocument> documents) {
		RawTextDocumentCorpus corpus = new RawTextDocumentCorpusImpl();
		documents.forEach(corpus::addDocument);
		return corpus;
	}

	static RawTextDocumentCorpus createCorpus(RawTextDocument... documents) {
		RawTextDocumentCorpus corpus = new RawTextDocumentCorpusImpl();
		Arrays.stream(documents).forEach(corpus::addDocument);
		return corpus;
	}

	static RawTextDocumentCorpus map(RawTextDocumentCorpus corpus, RawTextMapper mapper) {
		RawTextDocumentCorpus newCorpus = new RawTextDocumentCorpusImpl();
		corpus.getDocuments().stream().map(mapper).forEach(newCorpus::addDocument);
		return newCorpus;
	}

	static RawTextDocumentCorpus mapAll(RawTextDocumentCorpus corpus, RawTextMapper... mappers) {
		RawTextDocumentCorpus newCorpus = corpus;
		for (RawTextMapper mapper : mappers) {
			newCorpus = map(newCorpus, mapper);
		}
		return newCorpus;
	}

	static RawTextDocumentCorpus mapAll(RawTextDocumentCorpus corpus, List<RawTextMapper> mappers) {
		RawTextDocumentCorpus newCorpus = corpus;
		for (RawTextMapper mapper : mappers) {
			newCorpus = map(newCorpus, mapper);
		}
		return newCorpus;
	}

	static SegmentedTextDocumentCorpus createCorpus(SegmentedTextDocument... documents) {
		SegmentedTextDocumentCorpus corpus = new SegmentedTextDocumentCorpusImpl();
		Arrays.stream(documents).forEach(corpus::addDocument);
		return corpus;
	}

	static SegmentedTextDocumentCorpus map(SegmentedTextDocumentCorpus corpus, SegmentedTextMapper mapper) {
		SegmentedTextDocumentCorpus newCorpus = new SegmentedTextDocumentCorpusImpl();
		corpus.getDocuments().stream().map(mapper).forEach(newCorpus::addDocument);
		return newCorpus;
	}

	static SegmentedTextDocumentCorpus mapAll(SegmentedTextDocumentCorpus corpus, SegmentedTextMapper... mappers) {
		SegmentedTextDocumentCorpus newCorpus = corpus;
		for (SegmentedTextMapper mapper : mappers) {
			newCorpus = map(newCorpus, mapper);
		}
		return newCorpus;
	}

	static SegmentedTextDocumentCorpus mapAll(SegmentedTextDocumentCorpus corpus,
			List<SegmentedTextMapper> mappers) {
		SegmentedTextDocumentCorpus newCorpus = corpus;
		for (SegmentedTextMapper mapper : mappers) {
			newCorpus = map(newCorpus, mapper);
		}
		return newCorpus;
	}

	static SegmentedTextDocumentCorpus split(RawTextDocumentCorpus corpus, Splitter splitter) {
		SegmentedTextDocumentCorpus newCorpus = new SegmentedTextDocumentCorpusImpl();
		corpus.getDocuments().stream().map(splitter).forEach(newCorpus::addDocument);
		return newCorpus;
	}

}
