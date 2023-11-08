package net.breezeware.cosspringproject.user.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    ADMIN("admin"), CUSTOMER("customer"), CAFETERIASTAFF("cafeteria_staff"), DELIVERYSTAFF("delivery_staff");

    private final String name;
}
