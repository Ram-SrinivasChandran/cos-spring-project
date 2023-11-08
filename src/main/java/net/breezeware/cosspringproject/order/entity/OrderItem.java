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
import javax.validation.constraints.NotNull;

import net.breezeware.cosspringproject.food.entity.FoodItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(schema = "order_svc", name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq_gen")
    @SequenceGenerator(name = "order_item_seq_gen", sequenceName = "order_item_seq", schema = "order_svc",
            allocationSize = 1)
    private long id;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "food_item_id", referencedColumnName = "id")
    private FoodItem foodItem;

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
