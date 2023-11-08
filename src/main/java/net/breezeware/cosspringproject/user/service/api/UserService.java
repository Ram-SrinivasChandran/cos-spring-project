package net.breezeware.cosspringproject.user.service.api;

import net.breezeware.cosspringproject.user.entity.User;

public interface UserService extends GenericUserService<User, Long> {
    boolean isAAdmin(long id);

    boolean isACustomer(User user);

    boolean isACafeteriaStaff(long id);

    boolean isADeliveryStaff(long id);
}
