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
@Table(schema = "food_svc",name = "food_menu_availability_map")
public class FoodMenuAvailabilityMap {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "food_menu_availability_map_seq_gen")
    @SequenceGenerator(name = "food_menu_availability_map_seq_gen",sequenceName = "food_menu_availability_map_seq",schema = "food_svc",allocationSize = 1)
    private long id;
    @OneToOne
    @JoinColumn(name = "food_menu_id",referencedColumnName = "id")
    private FoodMenu foodMenuId;
    @OneToOne
    @JoinColumn(name = "availability_id",referencedColumnName = "id")
    private Availability availabilityId;
    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "modified_on")
    private Instant modifiedOn;
}
