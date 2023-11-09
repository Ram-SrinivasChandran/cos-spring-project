package net.breezeware.cosspringproject.order.dto;

import java.time.Instant;

import net.breezeware.cosspringproject.user.entity.UserAddressMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for placing an order, including user address
 * information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceOrderDto {
    /**
     * The user's address information for the order.
     */
    private UserAddressMap userAddressMap;

    /**
     * The email associated with the order.
     */
    private String email;

    /**
     * The phone number associated with the order.
     */
    private String phoneNumber;

    /**
     * The scheduled delivery time for the order.
     */
    private Instant deliveryTime;
}
