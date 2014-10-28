package fr.vinze.textanalysis.document;

import fr.vinze.textanalysis.corpus.CorpusUtils;
import fr.vinze.textanalysis.corpus.RawTextDocumentCorpus;
import fr.vinze.textanalysis.corpus.SegmentedTextDocumentCorpus;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.segmentation.impl.WordOnlySplitterImpl;

/**
 * The examples here are taken from the Handbook of LSA (Landauer & all)
 * chapter 2: Mathematical foundations behind Latent Semantic Analysis
 * 
 * @author Vinze
 *
 */
public abstract class LSAExampleCorpus {

	// the document contents (table 2.1)
	// 5 music documents, 4 bakery documents
	static final String 
		m1 = "Rock and Roll music in the 1960's",
		m2 = "Different Drum Rolls, a Demonstration of Techniques",
		m3 = "Drum and Bass Composition",
		m4 = "A perspective of Rock music in the 90's",
		m5 = "Music and Composition of Popular Bands",
		b1 = "How to make Bread and Rolls, a Demonstration",
		b2 = "Ingredients for Crescent Rolls",
		b3 = "A Recipe for Sourdough Bread",
		b4 = "A Quick Recipe for Pizza Dough using Organic Ingredients";
	// here only for example (the following "bag-of-word" version is used in the article)
	
	// the bag-of-word version of those documents used in table 2.2
	// projection on 10 terms only
	static final String
		m1BOW = "roll music",
		m2BOW = "drum roll demonstration",
		m3BOW = "drum composition",
		m4BOW = "rock music",
		m5BOW = "music composition",
		b1BOW = "bread roll demonstration",
		b2BOW = "ingredients roll",
		b3BOW = "recipe dough bread",
		b4BOW = "recipe dough ingredients";
	
	/**
	 * @return a corpus of segmented document corresponding to the example in the Handbook of LSA chapter 2
	 */
	public static SegmentedTextDocumentCorpus getCorpus() {
		RawTextDocumentCorpus rawCorpus = CorpusUtils.createCorpus(
				new RawTextDocumentImpl("m1", m1BOW),
				new RawTextDocumentImpl("m2", m2BOW),
				new RawTextDocumentImpl("m3", m3BOW),
				new RawTextDocumentImpl("m4", m4BOW),
				new RawTextDocumentImpl("m5", m5BOW),
				new RawTextDocumentImpl("b1", b1BOW),
				new RawTextDocumentImpl("b2", b2BOW),
				new RawTextDocumentImpl("b3", b3BOW),
				new RawTextDocumentImpl("b4", b4BOW));
		return CorpusUtils.split(rawCorpus, new WordOnlySplitterImpl());
	}
	
}
