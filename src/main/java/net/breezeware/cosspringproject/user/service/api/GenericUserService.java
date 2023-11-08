package net.breezeware.cosspringproject.user.service.api;

import java.util.List;

public interface GenericUserService<T, ID> {
    List<T> findAll();

    T findById(ID id);

    T save(T object);

    void update(ID id, T object);

    void delete(T object);

    void deleteById(ID id);
}
