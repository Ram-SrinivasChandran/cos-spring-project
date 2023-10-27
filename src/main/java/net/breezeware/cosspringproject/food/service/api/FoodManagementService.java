package net.breezeware.cosspringproject.food.service.api;

import java.util.Set;

public interface FoodManagementService <T,ID>{
    Set<T> findAll();
    T findById(ID id);
    T save(T object);
    void delete(T object);
    void deleteById(ID id);
}
