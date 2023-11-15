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

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entity class representing an item within an order, including the associated
 * food item.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(schema = "order_svc", name = "order_item")
@Schema(description = "Order Item in the Order")
public class OrderItem {
    /**
     * The unique identifier for the order item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq_gen")
    @SequenceGenerator(name = "order_item_seq_gen", sequenceName = "order_item_seq", schema = "order_svc",
            allocationSize = 1)
    @Schema(description = "Order Item Id", example = "1")
    private long id;

    /**
     * The order to which the item belongs.
     */
    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    /**
     * The food item associated with the order item.
     */
    @OneToOne
    @JoinColumn(name = "food_item_id", referencedColumnName = "id")
    private FoodItem foodItem;

    /**
     * The quantity of the food item in the order.
     */
    @NotNull(message = "Please Enter a Valid Quantity.")
    @Column(name = "quantity")
    @Schema(description = "Order Item Quantity", example = "1")
    private int quantity;

    /**
     * The cost of the order item.
     */
    @NotNull(message = "Please Enter a Valid Cost.")
    @Column(name = "cost")
    @Schema(description = "Order Item Cost", example = "10.0")
    private double cost;

    /**
     * The date and time when the order item was created.
     */
    @Column(name = "created_on")
    @Schema(description = "Order Item Creation Time", example = "2023-11-15T05:27:10.787Z")
    private Instant createdOn;

    /**
     * The date and time when the order item was last modified.
     */
    @Column(name = "modified_on")
    @Schema(description = "Order Item Modified Time", example = "2023-11-15T05:27:10.787Z")
    private Instant modifiedOn;
}
