package fr.vinze.textanalysis.document;

import java.util.Collection;

public interface Token {

	/**
	 * Can't have two metadatas with same {@link Metadata#getKey()} value.
	 * 
	 * @return
	 */
	Collection<Metadata<?>> getMetadatas();
	
	<T> Metadata<T> getMetadata(String key, Class<T> clazz);

	Metadata<?> getMetadata(String key);

	void addMetadata(Metadata<?> metadata);

	public static interface Metadata<T> {
		String getKey();

		T getValue();
	}
}
