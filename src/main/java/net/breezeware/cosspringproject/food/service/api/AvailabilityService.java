package net.breezeware.cosspringproject.food.service.api;

import net.breezeware.cosspringproject.food.entity.Availability;

import java.util.List;

public interface AvailabilityService {
    List<Availability> findAll();
    Availability findById(long id);
    Availability save(Availability object);
    void delete(Availability object);
    void deleteById(long id);
    void update(Long id, Availability availability);
    Availability findByDay(String day);
}
