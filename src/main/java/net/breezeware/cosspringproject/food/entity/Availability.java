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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(schema = "food_svc", name = "availability")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "availability_seq_gen")
    @SequenceGenerator(name = "availability_seq_gen", sequenceName = "availability_seq", schema = "food_svc",
            allocationSize = 1)
    private long id;

    @NotBlank(message = "Please Enter a valid Day.")
    @Column(name = "day")
    private String day;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "modified_on")
    private Instant modifiedOn;
}
