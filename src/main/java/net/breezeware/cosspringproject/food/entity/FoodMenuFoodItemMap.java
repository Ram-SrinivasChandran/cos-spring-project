package net.breezeware.cosspringproject.food.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(schema = "food_svc", name = "food_menu_food_item_map")
public class FoodMenuFoodItemMap {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "food_menu_food_item_map_seq_gen")
    @SequenceGenerator(name = "food_menu_food_item_map_seq_gen", sequenceName = "food_menu_food_item_map_seq",schema = "food_svc",allocationSize = 1)
    private long id;

    @OneToOne
    @JoinColumn(name = "food_item_id",referencedColumnName = "id")
    private FoodItem foodItem;

    @OneToOne
    @JoinColumn(name = "food_menu_id",referencedColumnName = "id")
    private FoodMenu foodMenu;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "modified_on")
    private Instant modifiedOn;
}
