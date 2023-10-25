package net.breezeware.cosspringproject.order.enumeration;

public enum Status {
    INCART("in_cart"),ORDERCANCELLED("order_cancelled"),ORDERPLACED("order_placed"), RECEIVEDORDER("received_order"),ORDERPREPAREDWAITINGFORDELIVERY("order_prepared_waiting_for_delivery"),PENDINGDELIVERY("pending_delivery"),ORDERDELIVERED("order_delivered");
    private String orderStatus;

    Status(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
