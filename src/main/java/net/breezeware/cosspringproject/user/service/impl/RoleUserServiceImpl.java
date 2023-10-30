package net.breezeware.cosspringproject.user.service.impl;

import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.user.dao.RoleRepository;
import net.breezeware.cosspringproject.user.entity.Role;
import net.breezeware.cosspringproject.user.service.api.RoleUserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RoleUserServiceImpl implements RoleUserService {

    private final RoleRepository roleRepository;

    public RoleUserServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return new ArrayList<>(roleRepository.findAll());
    }

    @Override
    public Role findById(Long aLong) {
        return roleRepository.findById(aLong).orElseThrow(()->new CustomException("Role is not Available for the Id", HttpStatus.NOT_FOUND));
    }

    @Override
    public Role save(Role object) {
        return roleRepository.save(object);
    }

    @Override
    public void update(Long aLong, Role object) {
    }

    @Override
    public void delete(Role object) {
        roleRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        roleRepository.deleteById(aLong);
    }
}
