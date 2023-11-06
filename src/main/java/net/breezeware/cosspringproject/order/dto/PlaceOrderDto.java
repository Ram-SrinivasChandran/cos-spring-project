package net.breezeware.cosspringproject.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.cosspringproject.user.entity.UserAddressMap;

import java.time.Instant;

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
