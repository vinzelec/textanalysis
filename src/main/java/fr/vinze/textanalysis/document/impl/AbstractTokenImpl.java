package fr.vinze.textanalysis.document.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.vinze.textanalysis.document.Token;

public abstract class AbstractTokenImpl implements Token {

	Map<String, Metadata<?>> metadatas;

	public AbstractTokenImpl() {
		this.metadatas = new HashMap<String, Token.Metadata<?>>();
	}

	public Collection<Metadata<?>> getMetadatas() {
		return metadatas.values();
	}

	public void addMetadata(Metadata<?> metadata) {
		this.metadatas.put(metadata.getKey(), metadata);
	}

	public Metadata<?> getMetadata(String key) {
		return metadatas.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> Metadata<T> getMetadata(String key, Class<T> clazz) {
		return (Metadata<T>) getMetadata(key);
	}

	public void mergeMetadata(Token from) {
		mergeMetadata(from, MergePolicy.IGNORE);
	}

	public void mergeMetadata(Token from, MergePolicy policy) {
		// TODO mergeMetadata

	}

	public abstract Token clone();
}
