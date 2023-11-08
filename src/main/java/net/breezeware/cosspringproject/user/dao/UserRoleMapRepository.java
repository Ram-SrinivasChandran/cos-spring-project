package net.breezeware.cosspringproject.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.cosspringproject.user.entity.User;
import net.breezeware.cosspringproject.user.entity.UserRoleMap;

@Repository
public interface UserRoleMapRepository extends JpaRepository<UserRoleMap, Long> {
    List<UserRoleMap> findByUser(User user);
}
