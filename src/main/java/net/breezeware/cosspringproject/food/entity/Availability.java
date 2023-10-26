package net.breezeware.cosspringproject.food.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "food_svc",name = "availability")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "availability_seq_gen")
    @SequenceGenerator(name = "availability_seq_gen",sequenceName = "availability_seq",schema = "food_svc",allocationSize = 1)
    private long id;
    @NotBlank(message = "Please Enter a valid Day.")
    @Column(name = "day")
    private String day;
    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "modified_on")
    private Instant modifiedOn;
}
