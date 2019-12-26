package de.clerkvest.api.implement.service;

import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.implement.service <p>
 * IService.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 19:27
 */
public interface IService<T> {

    /**
     * Saves an entity into the database.
     *
     * @param t Entity to save
     */
    public void save(T t);

    /**
     * Updates a existing entity database.
     *
     * @param t Entity to update
     */
    public void update(T t);

    /**
     * Gets all entities from the database
     *
     * @return A Optional list of the Entity
     */
    public List<T> getAll();

    /**
     * Gets the entity with the given id from the database
     * @param id Id to search for
     * @return The entity
     */
    public Optional<T> getById(long id);

    /**
     * Deletes the entity
     * @param t Entity to delete
     */
    public void delete(T t);

}
