package net.breezeware.cosspringproject.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.cosspringproject.food.entity.FoodItem;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @OneToOne
    @JoinColumn(name = "id")
    private Order orderId;
    @OneToOne
    @JoinColumn(name = "id")
    private FoodItem foodItemId;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "cost")
    private double cost;
    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "modified_on")
    private Instant modifiedOn;
}
