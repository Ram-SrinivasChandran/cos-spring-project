package net.breezeware.cosspringproject.food.service.api;

import java.util.List;

import net.breezeware.cosspringproject.food.entity.Availability;

/**
 * The `AvailabilityService` interface defines the contract for managing
 * availability of food Menus.
 */
public interface AvailabilityService {

    /**
     * Retrieve a list of all availabilities.
     * @return A list of all available availabilities.
     */
    List<Availability> findAllAvailabilities();

    /**
     * Find an availability by its unique identifier.
     * @param  availabilityId The unique identifier of the availability to find.
     * @return                The found availability or `null` if not found.
     */
    Availability findAvailabilityById(long availabilityId);

    /**
     * Save an availability.
     * @param  availability The availability to save.
     * @return              The saved availability.
     */
    Availability saveAvailability(Availability availability);

    /**
     * Delete an availability.
     * @param availability The availability to delete.
     */
    void deleteAvailability(Availability availability);

    /**
     * Delete an availability by its unique identifier.
     * @param availabilityId The unique identifier of the availability to delete.
     */
    void deleteAvailabilityById(long availabilityId);

    /**
     * Find an availability by the day of the week.
     * @param  day The day of the week to search for.
     * @return     The found availability for the specified day or `null` if not
     *             found.
     */
    Availability findAvailabilityByDay(String day);
}
