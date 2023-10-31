package net.breezeware.cosspringproject.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.exception.ValidationException;
import net.breezeware.cosspringproject.user.dao.RoleRepository;
import net.breezeware.cosspringproject.user.dao.UserRepository;
import net.breezeware.cosspringproject.user.dao.UserRoleMapRepository;
import net.breezeware.cosspringproject.user.entity.Role;
import net.breezeware.cosspringproject.user.entity.User;
import net.breezeware.cosspringproject.user.entity.UserRoleMap;
import net.breezeware.cosspringproject.user.service.api.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final RoleRepository roleRepository;
    private final Validator fieldValidator;


    @Override
    public List<User> findAll() {
        log.info("Entering findAll()");
        List<User> users = userRepository.findAll();
        log.info("Leaving findAll()");
        return users;
    }

    @Override
    public User findById(Long id) throws CustomException {
        log.info("Entering findById(), id: {}", id);
        if (id <= 0) {
            throw new CustomException("User Id Must be Greater Than Zero.", HttpStatus.BAD_REQUEST);
        }
        log.info("Leaving findById()");
        return userRepository.findById(id).orElseThrow(() ->new CustomException("The User not Found", HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Override
    public User save(User user) {
        log.info("Entering save()");
        Set<ConstraintViolation<User>> constraintViolationSet = fieldValidator.validate(user);
        ValidationException.handlingException(constraintViolationSet);
        if (user.getRoleId() <= 0) {
            throw new CustomException("Role Id Must be Greater Than Zero.", HttpStatus.BAD_REQUEST);
        }
        Role role = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new CustomException("Invalid role", HttpStatus.BAD_REQUEST));
        user.setCreatedOn(Instant.now());
        user.setModifiedOn(Instant.now());
        User savedUser = userRepository.save(user);
        UserRoleMap userRoleMap = new UserRoleMap();
        userRoleMap.setRole(role);
        userRoleMap.setUser(savedUser);
        userRoleMap.setCreatedOn(Instant.now());
        userRoleMap.setModifiedOn(Instant.now());
        userRoleMapRepository.save(userRoleMap);
        log.info("Leaving save()");
        return savedUser;
    }

    @Transactional
    @Override
    public void update(Long id, User updatedUser) {
        log.info("Entering update()");
        Set<ConstraintViolation<User>> constraintViolationSet = fieldValidator.validate(updatedUser);
        ValidationException.handlingException(constraintViolationSet);
        User user = findById(id);
        user.setUserName(updatedUser.getUserName());
        user.setPassword(updatedUser.getPassword());
        user.setName(updatedUser.getName());
        user.setModifiedOn(Instant.now());
        userRepository.save(user);
        log.info("Leaving update()");
    }

    @Transactional
    @Override
    public void delete(User user) {
        log.info("Entering delete()");
        userRepository.delete(user);
        log.info("Leaving delete()");
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        log.info("Entering deleteById()");
        User user = findById(id);
        List<UserRoleMap> userRoles = userRoleMapRepository.findByUser(user);
        userRoles.forEach(userRoleId -> {
            userRoleMapRepository.deleteById(userRoleId.getId());
        });
        userRepository.deleteById(id);
        log.info("Leaving deleteById()");
    }

}
