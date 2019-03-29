package fr.vinze.textanalysis.document.impl;

import fr.vinze.textanalysis.document.Token.Metadata;
import fr.vinze.utils.ObjectUtils;
import org.apache.commons.lang3.exception.CloneFailedException;

public class MetadataImpl<T> implements Metadata<T> {

	private final String key;
	private T value;

	public MetadataImpl(Metadata<T> metadata) {
		super();
		this.key = metadata.getKey();
		// try cloning value otherwise copy it by value
		try {
			this.value = ObjectUtils.clone(metadata.getValue());
		} catch (CloneFailedException | CloneNotSupportedException e) {
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

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public Metadata<T> clone() {
		return new MetadataImpl<>(this);
	}

}
