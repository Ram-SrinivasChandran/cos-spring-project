package net.breezeware.cosspringproject.food.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import net.breezeware.cosspringproject.food.entity.Availability;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    Availability findByDay(String day);
}
