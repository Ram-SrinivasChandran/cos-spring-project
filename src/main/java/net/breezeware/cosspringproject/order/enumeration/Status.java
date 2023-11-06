package net.breezeware.cosspringproject.order.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    INCART("in_cart"),ORDERCANCELLED("order_cancelled"),ORDERPLACED("order_placed"), RECEIVEDORDER("received_order"),ORDERPREPAREDWAITINGFORDELIVERY("order_prepared_waiting_for_delivery"),PENDINGDELIVERY("pending_delivery"),ORDERDELIVERED("order_delivered");
    private String orderStatus;
}
