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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(schema = "food_svc", name = "food_menu")
public class FoodMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_menu_seq_gen")
    @SequenceGenerator(name = "food_menu_seq_gen", sequenceName = "food_menu_seq", schema = "food_svc",
            allocationSize = 1)
    private long id;

    @NotBlank(message = "Please Enter a Valid Name.")
    @Size(min = 1, max = 20, message = "Food Menu type should be greater than or equal to 1")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Please Enter a Valid Type.")
    @Size(min = 1, max = 20, message = "Food Menu type should be greater than or equal to 1")
    @Column(name = "type")
    private String type;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "modified_on")
    private Instant modifiedOn;
}
