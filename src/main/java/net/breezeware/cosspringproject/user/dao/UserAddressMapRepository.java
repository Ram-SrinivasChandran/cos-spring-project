package net.breezeware.cosspringproject.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.cosspringproject.user.entity.UserAddressMap;

@Repository
public interface UserAddressMapRepository extends JpaRepository<UserAddressMap, Long> {
}
