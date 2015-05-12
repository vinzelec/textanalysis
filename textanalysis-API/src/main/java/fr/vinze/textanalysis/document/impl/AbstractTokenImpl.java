package fr.vinze.textanalysis.document.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.vinze.textanalysis.document.Token;

// IMPROVE maybe since java 8 everything in abstract class can be moved to interface as default implementation
public abstract class AbstractTokenImpl implements Token {

	private static final Logger log = LoggerFactory.getLogger(AbstractTokenImpl.class);

	Map<String, Metadata<?>> metadatas;

	public AbstractTokenImpl() {
		this.metadatas = new HashMap<String, Token.Metadata<?>>();
	}

	public String getUniqueID() {
		return toString();
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
					log.debug("failed to merge two metadata for key " + mdTo.getKey(), e);
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

	public abstract Token clone();
}
