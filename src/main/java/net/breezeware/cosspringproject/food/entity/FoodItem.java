package net.breezeware.cosspringproject.food.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(schema = "food_svc", name = "food_item")
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "food_item_seq_gen")
    @SequenceGenerator(name = "food_item_seq_gen",sequenceName = "food_item_seq",schema = "food_svc",allocationSize = 1)
    private long id;

    @Column(name = "name", length = 30)
    @NotBlank(message = "Please Provide a Valid Name.")
    @Size(min = 1, max = 20)
    private String name;

    @NotNull(message = "Please Enter a Valid Cost.")
    @Column(name = "cost")
    private double cost;

    @NotNull(message = "Please Enter a Valid Quantity.")
    @Column(name = "quantity")
    private int quantity;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "modified_on")
    private Instant modifiedOn;
}
