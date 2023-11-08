package net.breezeware.cosspringproject.food.enumeration;

public enum Days {
    MONDAY("monday"), TUESDAY("tuesday"), WEDNESDAY("wednesday"), THURSDAY("thursday"), FRIDAY("friday"),
    SATURDAY("saturday"), SUNDAY("sunday");

    public final String name;

    Days(String name) {
        this.name = name;
    }
}
