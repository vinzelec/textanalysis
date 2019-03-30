package fr.vinze.utils;

import org.apache.commons.lang3.exception.CloneFailedException;

import java.lang.reflect.InvocationTargetException;

public interface ObjectUtils {

	/**
	 * <quote> "The Cloneable interface was intended as a mixin interface for
	 * objects to advertise that they permit cloning. Unfortunately it fails to
	 * serve this purpose..." Josh Bloch </quote>
	 * <p>
	 * So forced to use reflection
	 * </p>
	 *
	 * @param object object to clone
	 * @return the clone of the object
	 * @throws CloneNotSupportedException if the object is not cloneable
	 */
	@SuppressWarnings("unchecked")
	public static <T> T clone(T object) throws CloneNotSupportedException {
		if (!(object instanceof Cloneable)) {
			throw new CloneNotSupportedException("object's class does not implements Cloneable");
		}
		try {
			return (T) object.getClass().getMethod("clone").invoke(object);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new CloneFailedException(e);
		}
	}

}
