package net.breezeware.cosspringproject.user.service.api;

import net.breezeware.cosspringproject.user.entity.Role;

/**
 * The `RoleService` interface extends the `GenericUserService` interface for managing user roles in the system.
 * It provides methods for handling user role entities.
 */
public interface RoleService extends GenericUserService<Role, Long> {
    // This interface does not define any additional methods beyond those provided by GenericUserService.
}
