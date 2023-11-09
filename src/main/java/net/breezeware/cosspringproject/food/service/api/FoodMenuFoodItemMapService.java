package net.breezeware.cosspringproject.food.service.api;

import java.util.List;

import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuFoodItemMap;

/**
 * The `FoodMenuFoodItemMapService` interface defines the contract for managing associations between food menus and food items.
 */
public interface FoodMenuFoodItemMapService {

    /**
     * Retrieve a list of all food menu food item mappings.
     *
     * @return A list of all available food menu food item mappings.
     */
    List<FoodMenuFoodItemMap> findAllFoodMenuFoodItemMap();

    /**
     * Find a food menu food item mapping by its unique identifier.
     *
     * @param foodMenuFoodItemMapId The unique identifier of the food menu food item mapping to find.
     * @return The found food menu food item mapping or `null` if not found.
     */
    FoodMenuFoodItemMap findFoodMenuFoodItemMapById(long foodMenuFoodItemMapId);

    /**
     * Save a food menu food item mapping.
     *
     * @param foodMenuFoodItemMap The food menu food item mapping to save.
     * @return The saved food menu food item mapping.
     */
    FoodMenuFoodItemMap saveFoodMenuFoodItemMap(FoodMenuFoodItemMap foodMenuFoodItemMap);

    /**
     * Get all food menu food item mappings associated with a specific food menu.
     *
     * @param foodMenu The food menu for which to retrieve food item mappings.
     * @return A list of food menu food item mappings associated with the given food menu.
     */
    List<FoodMenuFoodItemMap> getFoodMenuFoodItemMapByFoodMenu(FoodMenu foodMenu);

    /**
     * Delete a food menu food item mapping.
     *
     * @param foodMenuFoodItemMap The food menu food item mapping to delete.
     */
    void deleteFoodMenuFoodItemMap(FoodMenuFoodItemMap foodMenuFoodItemMap);

    /**
     * Delete a food menu food item mapping by its unique identifier.
     *
     * @param foodMenuFoodItemMapId The unique identifier of the food menu food item mapping to delete.
     */
    void deleteFoodMenuFoodItemMapById(long foodMenuFoodItemMapId);
}
