package net.breezeware.cosspringproject.food.enumeration;

/**
 * The `Days` enum represents the days of the week.
 */
public enum Days {
    /**
     * Monday.
     */
    MONDAY("monday"),

    /**
     * Tuesday.
     */
    TUESDAY("tuesday"),

    /**
     * Wednesday.
     */
    WEDNESDAY("wednesday"),

    /**
     * Thursday.
     */
    THURSDAY("thursday"),

    /**
     * Friday.
     */
    FRIDAY("friday"),

    /**
     * Saturday.
     */
    SATURDAY("saturday"),

    /**
     * Sunday.
     */
    SUNDAY("sunday");

    /**
     * The name of the day.
     */
    public final String name;

    /**
     * Constructs a `Days` enum value with the specified name.
     *
     * @param name The name of the day.
     */
    Days(String name) {
        this.name = name;
    }
}
