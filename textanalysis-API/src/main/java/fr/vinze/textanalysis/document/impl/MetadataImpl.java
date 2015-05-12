package fr.vinze.textanalysis.document.impl;

import org.apache.commons.lang3.exception.CloneFailedException;

import fr.vinze.textanalysis.document.Token.Metadata;
import fr.vinze.utils.ObjectUtils;

public class MetadataImpl<T> implements Metadata<T> {

	final String key;
	T value;

	public MetadataImpl(Metadata<T> metadata) {
		super();
		this.key = metadata.getKey();
		// try cloning value otherwise copy it by value
		try {
			this.value = ObjectUtils.clone(metadata.getValue());
		} catch (CloneFailedException e) {
			this.value = metadata.getValue();
		} catch (CloneNotSupportedException e) {
			this.value = metadata.getValue();
		}
	}

	public MetadataImpl(String key) {
		this(key, null);
	}

	public MetadataImpl(String key, T value) {
		super();
		this.key = key;
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public Metadata<T> clone() {
		return new MetadataImpl<T>(this);
	}

}
