package fr.vinze.textanalysis.persistence;

import java.util.Arrays;
import java.util.Collection;

/**
 * Super interface for all persistence DAO of the library
 * 
 * @author Vinze
 *
 * @param <T>
 *            type of instances handled by this DAO
 */
public interface DAO<T> {

	/**
	 * Saves an object (or creates it if not persisted yet) then returns valid instance
	 * (if object detached from persistence framework session the instance passed as parameter
	 * will be out-of-date).
	 * 
	 * @param obj
	 * @return
	 */
	T saveOrCreate(T obj);

	/**
	 * Loads an instance by identifier
	 * 
	 * @param id
	 * @return
	 */
	T get(String id);

	/**
	 * Retrieves a list of instance from a list of identifiers
	 * 
	 * @param ids
	 * @return
	 */
	default Collection<T> list(String... ids) {
		return list(Arrays.asList(ids));
	}

	/**
	 * Retrieves a list of instance from a collection of identifiers
	 * 
	 * @param ids
	 * @return
	 */
	Collection<T> list(Collection<String> ids);

	// TODO batch create ?

	/**
	 * batch save of a list of instances
	 * 
	 * @param list
	 */
	default void saveAll(T[] list) {
		saveAll(Arrays.asList(list));
	}

	/**
	 * batch save of a collection of instances
	 * 
	 * @param collection
	 */
	void saveAll(Collection<T> collection);

}
