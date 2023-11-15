package net.breezeware.cosspringproject.user.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents the mapping of a user to their address details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Schema(description = "User Address Map")
@Builder
@Table(schema = "user_svc", name = "user_address_map")
public class UserAddressMap {
    /**
     * The unique identifier for the user address mapping.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_address_map_seq_gen")
    @SequenceGenerator(name = "user_address_map_seq_gen", sequenceName = "user_address_map_seq", schema = "user_svc",
            allocationSize = 1)
    @Schema(description = "User Address Map Id", example = "1")
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * The door number of the user's address. Must be a valid value.
     */
    @NotBlank(message = "Please Enter a Valid Door Number.")
    @Size(min = 1, max = 20)
    @Column(name = "door_number", length = 20, nullable = false)
    @Schema(description = "User's Door Number",example = "123")
    private String doorNumber;

    /**
     * The street name of the user's address. Must be a valid value.
     */
    @NotBlank(message = "Please Enter a Valid Street Name.")
    @Size(min = 1, max = 20)
    @Column(name = "street_name", length = 20, nullable = false)
    @Schema(description = "User's Street Name",example = "Thirumal Nagar")
    private String streetName;

    /**
     * The city of the user's address. Must be a valid value.
     */
    @NotBlank(message = "Please Enter a Valid City.")
    @Size(min = 1, max = 20)
    @Column(name = "city", length = 20, nullable = false)
    @Schema(description = "User's City",example = "Dindigul")
    private String city;

    /**
     * The district of the user's address. Must be a valid value.
     */
    @NotBlank(message = "Please Enter a Valid District.")
    @Size(min = 1, max = 20)
    @Column(name = "district", length = 20, nullable = false)
    @Schema(description = "User's District",example = "Dindigul")
    private String district;

    /**
     * The state of the user's address. Must be a valid value.
     */
    @NotBlank(message = "Please Enter a Valid State.")
    @Size(min = 1, max = 20)
    @Column(name = "state", length = 20, nullable = false)
    @Schema(description = "User's State",example = "Tamil Nadu")
    private String state;

    /**
     * The PIN code of the user's address. Must be a valid value.
     */
    @NotNull(message = "Please Enter a Valid PinCode.")
    @Schema(description = "User's PinCode",example = "624001")
    @Column(name = "pincode", nullable = false)
    private long pincode;

    /**
     * The landmark associated with the user's address. Must be a valid value.
     */
    @NotBlank(message = "Please Enter a Valid Landmark.")
    @Size(min = 1, max = 20)
    @Column(name = "landmark", length = 20, nullable = false)
    @Schema(description = "User's LandMark",example = "Ayappan Kovil")
    private String landmark;
    /**
     * The timestamp when this mapping was created.
     */
    @Column(name = "created_on")
    @Schema(description = "User Address Map Created on",example = "2023-11-15T05:27:10.787Z")
    private Instant createdOn;
    /**
     * The timestamp when this mapping was last modified.
     */
    @Column(name = "modified_on")
    @Schema(description = "User Address Map Modified on",example = "2023-11-15T05:27:10.787Z")
    private Instant modifiedOn;
}
