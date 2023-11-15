package net.breezeware.cosspringproject.food.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The `FoodItem` entity class represents an item of food available in a menu.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Schema(description = "Food Item")
@Table(schema = "food_svc", name = "food_item", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class FoodItem {
    /**
     * The unique identifier for the food item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_item_seq_gen")
    @SequenceGenerator(name = "food_item_seq_gen", sequenceName = "food_item_seq", schema = "food_svc",
            allocationSize = 1)
    @Schema(description = "Food Item Id",example = "1")
    private long id;

    /**
     * The name of the food item, which must be unique.
     */
    @Column(name = "name", length = 30, unique = true)
    @NotBlank(message = "Please Provide a Valid Name.")
    @Size(min = 1, max = 20, message = "FoodItem Name Size should be lesser than 20")
    @Schema(description = "Food Item Name",example = "Dosa")
    private String name;

    /**
     * The cost of the food item.
     */
    @NotNull(message = "Please Enter a Valid Cost.")
    @Column(name = "cost")
    @Schema(description = "Food Item Cost",example = "20.0")
    private double cost;

    /**
     * The quantity of the food item available.
     */
    @NotNull(message = "Please Enter a Valid Quantity.")
    @Column(name = "quantity")
    @Schema(description = "Food Item Quantity",example = "1")
    private int quantity;

    /**
     * The timestamp indicating when the food item was created.
     */
    @Column(name = "created_on")
    @Schema(description = "Food Item Created on",example = "2023-11-15T05:27:10.787Z")
    private Instant createdOn;

    /**
     * The timestamp indicating when the food item was last modified.
     */
    @Column(name = "modified_on")
    @Schema(description = "Food Item Modified on",example = "2023-11-15T05:27:10.787Z")
    private Instant modifiedOn;
}
