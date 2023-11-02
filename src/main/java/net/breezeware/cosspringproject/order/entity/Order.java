package net.breezeware.cosspringproject.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.cosspringproject.user.entity.User;
import net.breezeware.cosspringproject.user.entity.UserAddressMap;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "order_svc", name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "order_seq_gen")
    @SequenceGenerator(name = "order_seq_gen", sequenceName = "order_seq",schema = "order_svc",allocationSize = 1)
    private long id;
    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @NotNull(message = "Please Enter a Valid Cost.")
    @Column(name = "total_cost")
    private double totalCost;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private long phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_address_id",referencedColumnName = "id")
    private UserAddressMap userAddressId;

    @Size(min = 1,max = 20)
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
