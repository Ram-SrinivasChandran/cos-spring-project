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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(schema = "food_svc", name = "food_menu_food_item_map")
public class FoodMenuFoodItemMap {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_menu_food_item_map_seq_gen")
    @SequenceGenerator(name = "food_menu_food_item_map_seq_gen", sequenceName = "food_menu_food_item_map_seq",
            schema = "food_svc", allocationSize = 1)
    private long id;

    @OneToOne
    @JoinColumn(name = "food_item_id", referencedColumnName = "id")
    private FoodItem foodItem;

    @OneToOne
    @JoinColumn(name = "food_menu_id", referencedColumnName = "id")
    private FoodMenu foodMenu;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "modified_on")
    private Instant modifiedOn;
}
