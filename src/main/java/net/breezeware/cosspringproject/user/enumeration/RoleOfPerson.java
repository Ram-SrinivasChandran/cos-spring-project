package net.breezeware.cosspringproject.user.enumeration;

public enum RoleOfPerson {
    ADMIN("admin"),CUSTOMER("customer"),CAFETERIASTAFF("cafeteria_staff"),DELIVERYSTAFF("delivery_staff");
    private final String name;

    RoleOfPerson(String name) {
        this.name=name;
    }
}
