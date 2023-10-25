package net.breezeware.cosspringproject.order.entity;

import net.breezeware.cosspringproject.food.entity.FoodItem;

import java.time.Instant;

public class OrderItem {
    private int id;
    private Order orderId;
    private FoodItem foodItemId;
    private int quantity;
    private double cost;
    private Instant createdOn;
    private Instant modifiedOn;
}
