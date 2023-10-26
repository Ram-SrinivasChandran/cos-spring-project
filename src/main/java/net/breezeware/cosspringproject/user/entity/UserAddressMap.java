package net.breezeware.cosspringproject.user.entity;

import lombok.AllArgsConstructor;
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
@Table(schema = "user_svc", name = "user_address_map")
public class UserAddressMap {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_address_map_seq_gen")
    @SequenceGenerator(name = "user_address_map_seq_gen", sequenceName = "user_address_map_seq",schema = "user_svc",allocationSize = 1)
    private long id;
    @OneToOne
    @JoinColumn(name = "id")
    private User userId;
    @NotBlank(message = "Please Enter a Valid Door Number.")
    @Size(min = 1,max = 20)
    @Column(name = "door_number")
    private String doorNumber;
    @NotBlank(message = "Please Enter a Valid Street Name.")
    @Size(min = 1,max = 20)
    @Column(name = "street_name")
    private String streetName;
    @NotBlank(message = "Please Enter a Valid City.")
    @Size(min = 1,max = 20)
    @Column(name = "city")
    private String city;
    @NotBlank(message = "Please Enter a Valid District.")
    @Size(min = 1,max = 20)
    @Column(name = "district")
    private String district;
    @NotBlank(message = "Please Enter a Valid State.")
    @Size(min = 1,max = 20)
    @Column(name = "state")
    private String state;
    @NotBlank(message = "Please Enter a Valid PinCode.")
    @Size(min = 1,max = 20)
    @Column(name = "pincode")
    private long pincode;
    @NotBlank(message = "Please Enter a Valid LandMark.")
    @Size(min = 1,max = 20)
    @Column(name = "landmark")
    private String landmark;
    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "modified_on")
    private Instant modifiedOn;
}
