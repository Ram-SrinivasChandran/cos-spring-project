package net.breezeware.cosspringproject.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.cosspringproject.food.entity.FoodItem;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "order_svc", name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "order_item_seq_gen")
    @SequenceGenerator(name = "order_item_seq_gen", sequenceName = "order_item_seq",schema = "order_svc",allocationSize = 1)
    private long id;
    @OneToOne
    @JoinColumn(name = "id")
    private Order orderId;
    @OneToOne
    @JoinColumn(name = "id")
    private FoodItem foodItemId;
    @NotNull(message = "Please Enter a Valid Quantity.")
    @Column(name = "quantity")
    private int quantity;
    @NotNull(message = "Please Enter a Valid Cost.")
    @Column(name = "cost")
    private double cost;
    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "modified_on")
    private Instant modifiedOn;
}
