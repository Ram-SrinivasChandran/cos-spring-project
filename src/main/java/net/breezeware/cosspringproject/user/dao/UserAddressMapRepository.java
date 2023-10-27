package net.breezeware.cosspringproject.user.dao;

import net.breezeware.cosspringproject.user.entity.UserAddressMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressMapRepository extends JpaRepository<UserAddressMap,Long> {
}
