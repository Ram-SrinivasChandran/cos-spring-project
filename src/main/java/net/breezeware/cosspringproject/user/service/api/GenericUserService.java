package net.breezeware.cosspringproject.user.service.api;

import java.util.List;

/**
 * The `GenericUserService` interface provides a generic set of methods for
 * managing entities in the system. It defines common operations for retrieving,
 * saving, updating, and deleting entities.
 * @param <T>  The type of entity to be managed.
 * @param <ID> The type of the entity's unique identifier.
 */
public interface GenericUserService<T, ID> {
    /**
     * Retrieves a list of all entities of the specified type.
     * @return A list of all entities.
     */
    List<T> findAll();

    /**
     * Retrieves an entity by its unique identifier.
     * @param  id The unique identifier of the entity.
     * @return    The entity with the specified identifier, or null if not found.
     */
    T findById(ID id);

    /**
     * Saves an entity, either creating a new one or updating an existing one.
     * @param  object The entity to be saved.
     * @return        The saved entity.
     */
    T save(T object);

    /**
     * Updates an existing entity with the specified identifier.
     * @param id     The unique identifier of the entity to be updated.
     * @param object The updated entity data.
     */
    void update(ID id, T object);

    /**
     * Deletes an entity.
     * @param object The entity to be deleted.
     */
    void delete(T object);

    /**
     * Deletes an entity by its unique identifier.
     * @param id The unique identifier of the entity to be deleted.
     */
    void deleteById(ID id);
}
