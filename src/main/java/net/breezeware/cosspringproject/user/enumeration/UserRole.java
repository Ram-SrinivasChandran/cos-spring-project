package net.breezeware.cosspringproject.user.enumeration;

public enum UserRole {
    ADMIN("admin"),CUSTOMER("customer"),CAFETERIASTAFF("cafeteria_staff"),DELIVERYSTAFF("delivery_staff");
    private final String name;

    UserRole(String name) {
        this.name=name;
    }
}
