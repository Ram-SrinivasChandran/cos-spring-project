package net.breezeware.cosspringproject.food.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "food_svc", name = "food_menu_food_item_map")
public class FoodMenuFoodItemMap {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "food_menu_food_item_map_seq_gen")
    @SequenceGenerator(name = "food_menu_food_item_map_seq_gen", sequenceName = "food_menu_food_item_map_seq",schema = "food_svc",allocationSize = 1)
    private long id;

    @OneToOne
    @JoinColumn(name = "food_item_id",referencedColumnName = "id")
    private FoodItem foodItemId;

    @OneToOne
    @JoinColumn(name = "food_menu_id",referencedColumnName = "id")
    private FoodMenu foodMenuId;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "modified_on")
    private Instant modifiedOn;
}
