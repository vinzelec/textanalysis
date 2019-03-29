package fr.vinze.textanalysis.mapper.impl;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.SegmentedTextDocument;
import fr.vinze.textanalysis.document.Token;
import fr.vinze.textanalysis.document.Word;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.document.impl.SegmentedTextDocumentImpl;
import fr.vinze.textanalysis.document.impl.WordImpl;
import fr.vinze.textanalysis.mapper.RawTextMapper;
import fr.vinze.textanalysis.mapper.SegmentedTextMapper;

public class ToLowercase implements RawTextMapper {

    public RawTextDocument apply(RawTextDocument document) {
		return new RawTextDocumentImpl(document.getName(), document.getContent().toLowerCase(), document.getRawSource());
    }
}
