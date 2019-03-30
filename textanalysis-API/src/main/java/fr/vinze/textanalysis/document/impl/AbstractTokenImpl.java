package fr.vinze.textanalysis.document.impl;

import fr.vinze.textanalysis.document.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.OperationNotSupportedException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// IMPROVE maybe since java 8 everything in abstract class can be moved to interface as default implementation
public abstract class AbstractTokenImpl implements Token {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTokenImpl.class);

	private Map<String, Metadata<?>> metadatas;

	AbstractTokenImpl() {
		this.metadatas = new HashMap<>();
	}

	@Override
	public String getUniqueID() {
		return toString();
	}

	@Override
	public Collection<Metadata<?>> getMetadatas() {
		return metadatas.values();
	}

	@Override
	public void addMetadata(Metadata<?> metadata) {
		this.metadatas.put(metadata.getKey(), metadata);
	}

	@Override
	public Metadata<?> getMetadata(String key) {
		return metadatas.get(key);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Metadata<T> getMetadata(String key, Class<T> clazz) {
		return (Metadata<T>) getMetadata(key);
	}

	@Override
	public void mergeMetadata(Token from) {
		mergeMetadata(from, MergePolicy.IGNORE);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void mergeMetadata(Token from, MergePolicy policy) {
		for (Metadata<?> mdFrom : from.getMetadatas()) {
			Metadata<?> mdTo = this.getMetadata(mdFrom.getKey());
			if (mdTo == null) {
				// don't have that metadata : copy it
				addMetadata(mdFrom.clone());
			} else if ((mdTo.getClass() == mdFrom.getClass()) && (mdFrom instanceof MergeableMetadata)) {
				try {
					((MergeableMetadata) mdTo).merge((MergeableMetadata) mdFrom);
				} catch (OperationNotSupportedException e) {
					LOGGER.debug("failed to merge two metadata for key " + mdTo.getKey(), e);
					// back to MergePolicy
					if (policy == MergePolicy.OVERRIDE) {
						// replace original by a copy of input
						addMetadata(mdFrom.clone());
					}
				}
			} else if (policy == MergePolicy.OVERRIDE) {
				// replace original by a copy of input
				addMetadata(mdFrom.clone());
			}
		}

	}

	@Override
	public abstract Token clone();

}
