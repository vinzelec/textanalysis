package fr.vinze.textanalysis.document.impl;

import fr.vinze.textanalysis.document.Token.MergeableMetadata;
import fr.vinze.textanalysis.document.Token.Metadata;

// IMPROVE maybe since java 8 everything in abstract class can be moved to interface as default implementation
public abstract class AbstractMergeableMetadata<T> extends MetadataImpl<T> implements MergeableMetadata<T> {

	public AbstractMergeableMetadata(Metadata<T> metadata) {
		super(metadata);
	}

	public AbstractMergeableMetadata(String key, T value) {
		super(key, value);
	}

	public AbstractMergeableMetadata(String key) {
		super(key);
	}

}
