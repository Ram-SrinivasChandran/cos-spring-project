package net.breezeware.cosspringproject.food.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food_menu_food_item_map")
public class FoodMenuFoodItemMap {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @OneToOne
    @JoinColumn(name = "id")
    private FoodItem foodItemId;
    @OneToOne
    @JoinColumn(name = "id")
    private FoodMenu foodMenuId;
    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "modified_on")
    private Instant modifiedOn;
}
