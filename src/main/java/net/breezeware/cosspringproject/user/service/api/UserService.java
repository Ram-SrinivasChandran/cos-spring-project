package net.breezeware.cosspringproject.user.service.api;

import net.breezeware.cosspringproject.user.entity.User;

/**
 * The `UserService` interface extends the `GenericUserService` interface for managing user entities in the system.
 * It provides additional methods for checking user roles and permissions.
 */
public interface UserService extends GenericUserService<User, Long> {
    /**
     * Checks if a user with the specified unique identifier is an admin.
     *
     * @param userId The unique identifier of the user to check.
     * @return `true` if the user is an admin; otherwise, `false`.
     */
    boolean isAdmin(long userId);

    /**
     * Checks if a user is a customer.
     *
     * @param userId The unique identifier of the user to check.
     * @return `true` if the user is a customer; otherwise, `false`.
     */
    boolean isCustomer(long userId);

    /**
     * Checks if a user with the specified unique identifier is cafeteria staff.
     *
     * @param userId The unique identifier of the user to check.
     * @return `true` if the user is cafeteria staff; otherwise, `false`.
     */
    boolean isCafeteriaStaff(long userId);

    /**
     * Checks if a user with the specified unique identifier is delivery staff.
     *
     * @param userId The unique identifier of the user to check.
     * @return `true` if the user is delivery staff; otherwise, `false`.
     */
    boolean isDeliveryStaff(long userId);
}
