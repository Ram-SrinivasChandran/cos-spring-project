package net.breezeware.cosspringproject.order.dto;

import java.time.Instant;

import net.breezeware.cosspringproject.user.entity.UserAddressMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) for placing an order, including user address
 * information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Place Order Dto")
public class PlaceOrderDto {
    /**
     * The user's address information for the order.
     */
    private UserAddressMap userAddressMap;

    /**
     * The email associated with the order.
     */
    @Schema(description = "Email of the User", example = "chand2ram@gmail.com")
    private String email;

    /**
     * The phone number associated with the order.
     */
    @Schema(description = "Phone Number of the User", example = "9677963066")
    private String phoneNumber;

    /**
     * The scheduled delivery time for the order.
     */
    @Schema(description = "Delivery Time", example = "2023-11-15T05:27:10.787Z")
    private Instant deliveryTime;
}
