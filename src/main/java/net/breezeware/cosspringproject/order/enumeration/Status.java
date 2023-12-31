package net.breezeware.cosspringproject.order.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An enumeration representing the possible status of an order.
 */
@Getter
@AllArgsConstructor
public enum Status {
    IN_CART("in_cart"), ORDER_CANCELLED("order_cancelled"), ORDER_PLACED("order_placed"),
    RECEIVED_ORDER("received_order"), ORDER_PREPARED_WAITING_FOR_DELIVERY("order_prepared_waiting_for_delivery"),
    PENDING_DELIVERY("pending_delivery"), ORDER_DELIVERED("order_delivered");

    /**
     * The status of an order.
     */
    private final String orderStatus;
}
