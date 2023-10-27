package net.breezeware.cosspringproject.food.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "food_svc", name = "food_menu")
public class FoodMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "food_menu_seq_gen")
    @SequenceGenerator(name = "food_menu_seq_gen", sequenceName = "food_menu_seq",schema = "food_svc",allocationSize = 1)
    private long id;

    @NotBlank(message = "Please Enter a Valid Name.")
    @Min(1)
    @Max(20)
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Please Enter a Valid Type.")
    @Size(min = 1,max = 20)
    @Column(name = "type")
    private String type;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "modified_on")
    private Instant modifiedOn;
}
