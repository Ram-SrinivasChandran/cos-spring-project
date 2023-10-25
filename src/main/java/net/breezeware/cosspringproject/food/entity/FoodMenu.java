package net.breezeware.cosspringproject.food.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.cosspringproject.food.enumeration.Days;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food_menu")
public class FoodMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "availability_on")
    private String availabilityOn;
    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "modified_on")
    private Instant modifiedOn;
}
