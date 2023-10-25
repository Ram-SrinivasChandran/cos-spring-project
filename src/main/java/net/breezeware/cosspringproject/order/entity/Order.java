package net.breezeware.cosspringproject.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.cosspringproject.user.entity.User;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "user_id")
    private User userId;
    @Column(name = "total_cost")
    private double totalCost;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private long phoneNumber;
    @Column(name = "user_address_id")
    private long userAddressId;
    @Column(name = "status")
    private String status;
    @Column(name = "order_on")
    private Instant orderOn;
    @Column(name = "delivery_on")
    private Instant deliveryOn;
    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "modified_on")
    private Instant modifiedOn;

}
