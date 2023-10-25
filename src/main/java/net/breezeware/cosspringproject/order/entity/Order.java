package net.breezeware.cosspringproject.order.entity;

import net.breezeware.cosspringproject.user.entity.User;
import java.time.Instant;

public class Order {
    private long id;
    private User userId;
    private double totalCost;
    private String email;
    private long phoneNumber;
    private String orderLocation;
    private String status;
    private Instant orderOn;
    private Instant deliveryOn;
    private Instant createdOn;
    private Instant modifiedOn;

}
