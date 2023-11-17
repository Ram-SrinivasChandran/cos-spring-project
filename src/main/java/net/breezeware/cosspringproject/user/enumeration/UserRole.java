package net.breezeware.cosspringproject.user.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The `UserRole` enumeration represents different user roles within the system.
 * Each role defines a set of permissions and responsibilities.
 */
@AllArgsConstructor
@Getter
public enum UserRole {
    /**
     * The `ADMIN` role represents an administrator with full system access and
     * privileges.
     */
    ADMIN("admin"),

    /**
     * The `CUSTOMER` role represents regular customers using the system.
     */
    CUSTOMER("customer"),

    /**
     * The `CAFETERIASTAFF` role represents staff members working in the cafeteria
     * or restaurant.
     */
    CAFETERIASTAFF("cafeteria_staff"),

    /**
     * The `DELIVERYSTAFF` role represents staff members responsible for order
     * delivery.
     */
    DELIVERYSTAFF("delivery_staff");

    /**
     * The name associated with the user role.
     */
    private final String name;
}
