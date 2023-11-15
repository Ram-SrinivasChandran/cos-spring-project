package net.breezeware.cosspringproject.food.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The `FoodMenuFoodItemMap` entity class represents the mapping between a food
 * menu and its associated food items.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(schema = "food_svc", name = "food_menu_food_item_map")
@Schema(description = "Food Menu Food Item Map")
public class FoodMenuFoodItemMap {
    /**
     * The unique identifier for the food menu-food item mapping.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_menu_food_item_map_seq_gen")
    @SequenceGenerator(name = "food_menu_food_item_map_seq_gen", sequenceName = "food_menu_food_item_map_seq",
            schema = "food_svc", allocationSize = 1)
    @Schema(description = "Food Menu Food Item Map Id", example = "1")
    private long id;

    /**
     * The food item associated with this map.
     */
    @OneToOne
    @JoinColumn(name = "food_item_id", referencedColumnName = "id")
    private FoodItem foodItem;

    /**
     * The food menu associated with this map.
     */
    @OneToOne
    @JoinColumn(name = "food_menu_id", referencedColumnName = "id")
    private FoodMenu foodMenu;

    /**
     * The timestamp indicating when the food menu-food item map was created.
     */
    @Column(name = "created_on")
    @Schema(description = "Food Menu Food Item Map Created on", example = "2023-11-15T05:27:10.787Z")
    private Instant createdOn;

    /**
     * The timestamp indicating when the food menu-food item map was last modified.
     */
    @Column(name = "modified_on")
    @Schema(description = "Food Menu Food Item Map Modified on", example = "2023-11-15T05:27:10.787Z")

    private Instant modifiedOn;
}
