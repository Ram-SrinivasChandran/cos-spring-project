package net.breezeware.cosspringproject.user.dao;

import net.breezeware.cosspringproject.user.entity.UserRoleMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMapRepository extends JpaRepository<UserRoleMap,Long> {
}
