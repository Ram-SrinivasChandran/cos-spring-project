package net.breezeware.cosspringproject.food.service.api;

import java.util.List;

import net.breezeware.cosspringproject.food.entity.FoodItem;

/**
 * The `FoodItemService` interface defines the contract for managing food items.
 */
public interface FoodItemService {

    /**
     * Retrieve a list of all food items.
     * @return A list of all available food items.
     */
    List<FoodItem> findAllFoodItems(long userId);

    /**
     * Find a food item by its unique identifier.
     * @param  foodItemId The unique identifier of the food item to find.
     * @return            The found food item or `null` if not found.
     */
    FoodItem findFoodItemById(long foodItemId);

    /**
     * Save a food item.
     * @param  foodItem The food item to save.
     * @return          The saved food item.
     */
    FoodItem saveFoodItem(FoodItem foodItem);

    /**
     * Delete a food item.
     * @param foodItem The food item to delete.
     */
    void deleteFoodItem(FoodItem foodItem);

    /**
     * Delete a food item by its unique identifier.
     * @param foodItemId The unique identifier of the food item to delete.
     */
    void deleteFoodItemById(long foodItemId);

    /**
     * Update a food item identified by its unique identifier.
     * @param foodItemId The unique identifier of the food item to update.
     * @param foodItem   The updated information for the food item.
     */
    void updateFoodItem(long foodItemId, FoodItem foodItem);
}
