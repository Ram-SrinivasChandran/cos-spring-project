package net.breezeware.cosspringproject.food.service.api;

import java.util.List;

import net.breezeware.cosspringproject.food.entity.Availability;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuAvailabilityMap;

/**
 * The `FoodMenuAvailabilityMapService` interface defines the contract for
 * managing associations between food menus and availability.
 */
public interface FoodMenuAvailabilityMapService {

    /**
     * Retrieve a list of all food menu availability mappings.
     * @return A list of all available food menu availability mappings.
     */
    List<FoodMenuAvailabilityMap> findAllFoodMenuAvailabilityMap();

    /**
     * Find a food menu availability mapping by its unique identifier.
     * @param  foodMenuAvailabilityMapId The unique identifier of the food menu
     *                                   availability mapping to find.
     * @return                           The found food menu availability mapping or
     *                                   `null` if not found.
     */
    FoodMenuAvailabilityMap findFoodMenuAvailabilityMapById(long foodMenuAvailabilityMapId);

    /**
     * Save a food menu availability mapping.
     * @param  foodMenuAvailabilityMap The food menu availability mapping to save.
     * @return                         The saved food menu availability mapping.
     */
    FoodMenuAvailabilityMap saveFoodMenuAvailabilityMap(FoodMenuAvailabilityMap foodMenuAvailabilityMap);

    /**
     * Delete a food menu availability mapping.
     * @param foodMenuAvailabilityMap The food menu availability mapping to delete.
     */
    void deleteFoodMenuAvailabilityMap(FoodMenuAvailabilityMap foodMenuAvailabilityMap);

    /**
     * Delete a food menu availability mapping by its unique identifier.
     * @param foodMenuAvailabilityMapId The unique identifier of the food menu
     *                                  availability mapping to delete.
     */
    void deleteFoodMenuAvailabilityMapById(long foodMenuAvailabilityMapId);

    /**
     * Get all food menu availability mappings associated with a specific food menu.
     * @param  foodMenu The food menu for which to retrieve availability mappings.
     * @return          A list of food menu availability mappings associated with
     *                  the given food menu.
     */
    List<FoodMenuAvailabilityMap> getFoodMenuAvailabilityMapByFoodMenu(FoodMenu foodMenu);

    /**
     * Get all food menu availability mappings associated with a specific
     * availability.
     * @param  availability The availability for which to retrieve food menu
     *                      mappings.
     * @return              A list of food menu availability mappings associated
     *                      with the given availability.
     */
    List<FoodMenuAvailabilityMap> getFoodMenuAvailabilityMapByAvailability(Availability availability);
}
