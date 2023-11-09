package net.breezeware.cosspringproject.order.entity;

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
import javax.validation.constraints.Size;

import net.breezeware.cosspringproject.user.entity.User;
import net.breezeware.cosspringproject.user.entity.UserAddressMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing an order with associated user and address
 * information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(schema = "order_svc", name = "order")
public class Order {
    /**
     * The unique identifier for the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq_gen")
    @SequenceGenerator(name = "order_seq_gen", sequenceName = "order_seq", schema = "order_svc", allocationSize = 1)
    private long id;

    /**
     * The user associated with the order.
     */
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * The total cost of the order.
     */
    @Column(name = "total_cost")
    private double totalCost;

    /**
     * The email associated with the order.
     */
    @NotBlank(message = "Please Enter the Email")
    @Column(name = "email")
    private String email;

    /**
     * The phone number associated with the order.
     */
    @NotBlank(message = "Please Enter the Phone Number")
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * The user address information for the order.
     */
    @OneToOne
    @JoinColumn(name = "user_address_id", referencedColumnName = "id")
    private UserAddressMap userAddress;

    /**
     * The status of the order.
     */
    @Size(min = 1, max = 35)
    @Column(name = "status")
    private String status;

    /**
     * The date and time when the order was created.
     */
    @Column(name = "order_on")
    private Instant orderOn;

    /**
     * The date and time when the order will be delivered.
     */
    @Column(name = "delivery_on")
    private Instant deliveryOn;

    /**
     * The date and time when the order was created.
     */
    @Column(name = "created_on")
    private Instant createdOn;

    /**
     * The date and time when the order was last modified.
     */
    @Column(name = "modified_on")
    private Instant modifiedOn;
}
