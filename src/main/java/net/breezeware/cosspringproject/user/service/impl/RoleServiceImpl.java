package net.breezeware.cosspringproject.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.user.dao.RoleRepository;
import net.breezeware.cosspringproject.user.entity.Role;
import net.breezeware.cosspringproject.user.service.api.RoleService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Role> findAll() {
        log.info("Entering findAll()");
        ArrayList<Role> roles = new ArrayList<>(roleRepository.findAll());
        log.info("Leaving findAll()");
        return roles;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Role findById(Long roleId) {
        log.info("Entering findById()");
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new CustomException("Role is not Available for the Id", HttpStatus.NOT_FOUND));
        log.info("Leaving findById()");
        return role;
    }
    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Role save(Role object) {
        log.info("Entering save()");
        Role savedRole = roleRepository.save(object);
        log.info("Leaving save()");
        return savedRole;
    }
    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void update(Long roleId, Role object) {

    }
    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(Role object) {
        log.info("Entering delete()");
        roleRepository.delete(object);
        log.info("Leaving delete()");
    }
    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteById(Long roleId) {
        log.info("Entering deleteById()");
        roleRepository.deleteById(roleId);
        log.info("Leaving deleteById()");
    }
}
