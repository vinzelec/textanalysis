package fr.vinze.textanalysis.document;

import java.util.Collection;

import javax.naming.OperationNotSupportedException;

public interface Token extends Cloneable {

	/**
	 * Can't have two metadatas with same {@link Metadata#getKey()} value.
	 * 
	 * @return
	 */
	Collection<Metadata<?>> getMetadatas();
	
	<T> Metadata<T> getMetadata(String key, Class<T> clazz);

	Metadata<?> getMetadata(String key);

	void addMetadata(Metadata<?> metadata);

	Token clone();

	/**
	 * a mechanism to merge metadatas of two token that are equals.
	 * It is equivalent to {@link #mergeMetadata(Token, MergePolicy)} with {@link MergePolicy#IGNORE} parameter
	 * 
	 * @param from
	 */
	void mergeMetadata(Token from);

	void mergeMetadata(Token from, MergePolicy policy);

	public static interface Metadata<T> extends Cloneable {
		String getKey();

		T getValue();

		void setValue(T value);

		Metadata<T> clone();
	}

	/**
	 * <p>
	 * interface for metadata that can be merged
	 * </p>
	 * 
	 * @author Vinze
	 *
	 * @param <T>
	 */
	public static interface MergeableMetadata<T> extends Metadata<T> {

		/**
		 * @param from
		 *            input metadata
		 * @throws OperationNotSupportedException
		 *             if the parameter is not of same class
		 */
		void merge(MergeableMetadata<T> from) throws OperationNotSupportedException;

	}

	/**
	 * <p>
	 * Merge policy for metadata that can't be merged (input override existing or is ignored).
	 * </p>
	 * 
	 * @author Vinze
	 *
	 */
	public static enum MergePolicy {
		OVERRIDE, IGNORE;
	}
}
