package net.breezeware.cosspringproject.user.dao;

import net.breezeware.cosspringproject.user.entity.User;
import net.breezeware.cosspringproject.user.entity.UserRoleMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapRepository extends JpaRepository<UserRoleMap,Long> {
    List<UserRoleMap> findByUser(User user);
}
