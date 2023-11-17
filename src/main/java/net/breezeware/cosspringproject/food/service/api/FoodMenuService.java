package net.breezeware.cosspringproject.food.service.api;

import java.util.List;

import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.FoodMenu;

/**
 * The `FoodMenuService` interface defines the contract for managing food menus.
 */
public interface FoodMenuService {

    /**
     * Retrieve a list of all food menus.
     * @return A list of all available food menus.
     */
    List<FoodMenu> findAllFoodMenu();

    /**
     * Find a food menu by its unique identifier.
     * @param  foodMenuId The unique identifier of the food menu to find.
     * @return            The found food menu or `null` if not found.
     */
    FoodMenu findFoodMenuById(long foodMenuId);

    /**
     * Create a new food menu based on the provided data.
     * @param  foodMenuDto The data for creating a new food menu.
     * @return             The created food menu.
     */
    FoodMenu saveFoodMenu(FoodMenuDto foodMenuDto);

    /**
     * Delete a food menu.
     * @param foodMenu The food menu to delete.
     */
    void deleteFoodMenu(FoodMenu foodMenu);

    /**
     * Delete a food menu by its unique identifier.
     * @param foodMenuId The unique identifier of the food menu to delete.
     */
    void deleteFoodMenuById(long foodMenuId);

    /**
     * Update an existing food menu with new data.
     * @param foodMenuId  The unique identifier of the food menu to update.
     * @param foodMenuDto The updated data for the food menu.
     */
    void updateFoodMenu(long foodMenuId, FoodMenuDto foodMenuDto);
}
