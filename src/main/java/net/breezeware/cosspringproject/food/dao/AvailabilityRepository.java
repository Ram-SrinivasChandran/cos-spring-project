package net.breezeware.cosspringproject.food.dao;

import net.breezeware.cosspringproject.food.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<Availability,Long> {
    Availability findByDay(String day);
}
