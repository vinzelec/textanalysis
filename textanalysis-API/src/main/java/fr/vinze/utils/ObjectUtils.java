package fr.vinze.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.exception.CloneFailedException;

public abstract class ObjectUtils {

	/**
	 * <quote> "The Cloneable interface was intended as a mixin interface for
	 * objects to advertise that they permit cloning. Unfortunately it fails to
	 * serve this purpose..." Josh Bloch </quote>
	 * <p>
	 * So forced to use reflection
	 * </p>
	 * 
	 * @param object
	 * @return
	 * @throws CloneNotSupportedException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T clone(T object) throws CloneNotSupportedException, CloneFailedException {
		if (!(object instanceof Cloneable))
			throw new CloneNotSupportedException("object's class does not implements Cloneable");
		try {
			return (T) object.getClass().getMethod("clone").invoke(object);
		} catch (IllegalAccessException e) {
			throw new CloneFailedException(e);
		} catch (IllegalArgumentException e) {
			throw new CloneFailedException(e);
		} catch (InvocationTargetException e) {
			throw new CloneFailedException(e);
		} catch (NoSuchMethodException e) {
			throw new CloneFailedException(e);
		} catch (SecurityException e) {
			throw new CloneFailedException(e);
		}
	}

}
