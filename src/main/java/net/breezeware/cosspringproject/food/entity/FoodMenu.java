package net.breezeware.cosspringproject.food.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The `FoodMenu` entity class represents a type or category of food menu.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Schema(description = "Food Menu")
@Table(schema = "food_svc", name = "food_menu")
public class FoodMenu {
    /**
     * The unique identifier for the food menu.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_menu_seq_gen")
    @SequenceGenerator(name = "food_menu_seq_gen", sequenceName = "food_menu_seq", schema = "food_svc",
            allocationSize = 1)
    @Schema(description = "Food Menu Id", example = "1")
    private long id;

    /**
     * The name of the food menu.
     */
    @NotBlank(message = "Please Enter a Valid Name.")
    @Size(min = 1, max = 20, message = "Food Menu Name should be greater than or equal to 1")
    @Column(name = "name")
    @Schema(description = "Food Menu Name", example = "BreakFast")
    private String name;

    /**
     * The type or category of the food menu.
     */
    @NotBlank(message = "Please Enter a Valid Type.")
    @Size(min = 1, max = 20, message = "Food Menu Type should be greater than or equal to 1")
    @Column(name = "type")
    @Schema(description = "Food Menu Type", example = "Veg")
    private String type;

    /**
     * The timestamp indicating when the food menu was created.
     */
    @Column(name = "created_on")
    @Schema(description = "Food Menu Created on", example = "2023-11-15T05:27:10.787Z")
    private Instant createdOn;

    /**
     * The timestamp indicating when the food menu was last modified.
     */
    @Column(name = "modified_on")
    @Schema(description = "Food Menu Modified on", example = "2023-11-15T05:27:10.787Z")
    private Instant modifiedOn;
}
