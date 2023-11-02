package net.breezeware.cosspringproject.user.service.api;

import net.breezeware.cosspringproject.user.entity.User;

public interface UserService extends GenericUserService<User,Long> {
    boolean isACustomer(User user);
}
