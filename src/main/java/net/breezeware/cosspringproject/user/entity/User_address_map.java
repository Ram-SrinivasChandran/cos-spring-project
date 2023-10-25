package net.breezeware.cosspringproject.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_address_map")
public class User_address_map {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @OneToOne
    @JoinColumn(name = "id")
    private User userId;
    @Column(name = "door_number")
    private String doorNumber;
    @Column(name = "street_name")
    private String streetName;
    @Column(name = "city")
    private String city;
    @Column(name = "district")
    private String district;
    @Column(name = "state")
    private String state;
    @Column(name = "pincode")
    private long pincode;
    @Column(name = "landmark")
    private String landmark;
    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "modified_on")
    private Instant modifiedOn;
}
