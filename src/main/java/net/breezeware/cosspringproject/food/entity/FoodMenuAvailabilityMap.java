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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The `FoodMenuAvailabilityMap` entity class represents the mapping between a
 * food menu and its availability schedule.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Schema(description = "Food Menu Availability Map")
@Table(schema = "food_svc", name = "food_menu_availability_map")
public class FoodMenuAvailabilityMap {
    /**
     * The unique identifier for the food menu availability map.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_menu_availability_map_seq_gen")
    @SequenceGenerator(name = "food_menu_availability_map_seq_gen", sequenceName = "food_menu_availability_map_seq",
            schema = "food_svc", allocationSize = 1)
    @Schema(description = "Food Menu Availability Map Id",example = "1")
    private long id;

    /**
     * The food menu associated with this availability map.
     */
    @OneToOne
    @JoinColumn(name = "food_menu_id", referencedColumnName = "id")
    private FoodMenu foodMenu;

    /**
     * The availability schedule associated with this map.
     */
    @OneToOne
    @JoinColumn(name = "availability_id", referencedColumnName = "id")
    private Availability availability;

    /**
     * The timestamp indicating when the food menu availability map was created.
     */
    @Column(name = "created_on")
    @Schema(description = "Food Menu Availability Map Created on",example = "2023-11-15T05:27:10.787Z")
    private Instant createdOn;

    /**
     * The timestamp indicating when the food menu availability map was last
     * modified.
     */
    @Column(name = "modified_on")
    @Schema(description = "Food Menu Availability Map Modified on",example = "2023-11-15T05:27:10.787Z")
    private Instant modifiedOn;
}
