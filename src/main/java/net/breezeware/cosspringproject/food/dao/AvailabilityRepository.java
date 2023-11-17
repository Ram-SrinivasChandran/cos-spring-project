package net.breezeware.cosspringproject.food.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.cosspringproject.food.entity.Availability;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    Availability findByDay(String day);
}
