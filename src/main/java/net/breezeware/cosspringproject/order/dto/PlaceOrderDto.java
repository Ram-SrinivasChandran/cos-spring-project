package net.breezeware.cosspringproject.order.dto;

import java.time.Instant;

import net.breezeware.cosspringproject.user.entity.UserAddressMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceOrderDto {
    private UserAddressMap userAddressMap;
    private String email;
    private String phoneNumber;
    private Instant deliveryTime;
}
