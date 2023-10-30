package net.breezeware.cosspringproject.food.service.api;

import java.util.List;

public interface GenericFoodService<T,ID>{
    List<T> findAll();
    T findById(ID id);
    T save(T object);
    void delete(T object);
    void deleteById(ID id);
}
