package fr.vinze.textanalysis.mapper.impl;

import fr.vinze.textanalysis.document.RawTextDocument;
import fr.vinze.textanalysis.document.impl.RawTextDocumentImpl;
import fr.vinze.textanalysis.mapper.RawTextMapper;

public class ToLowercase implements RawTextMapper {

    @Override
    public RawTextDocument apply(RawTextDocument document) {
		return new RawTextDocumentImpl(document.getName(), document.getContent().toLowerCase(), document.getRawSource());
    }
}
