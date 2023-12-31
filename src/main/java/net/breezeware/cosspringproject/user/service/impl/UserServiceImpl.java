package net.breezeware.cosspringproject.user.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.exception.ValidationException;
import net.breezeware.cosspringproject.user.dao.RoleRepository;
import net.breezeware.cosspringproject.user.dao.UserRepository;
import net.breezeware.cosspringproject.user.dao.UserRoleMapRepository;
import net.breezeware.cosspringproject.user.entity.Role;
import net.breezeware.cosspringproject.user.entity.User;
import net.breezeware.cosspringproject.user.entity.UserRoleMap;
import net.breezeware.cosspringproject.user.enumeration.UserRole;
import net.breezeware.cosspringproject.user.service.api.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final RoleRepository roleRepository;
    private final Validator fieldValidator;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll() {
        log.info("Entering findAll()");
        List<User> users = userRepository.findAll();
        log.info("Leaving findAll()");
        return users;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findById(Long userId) throws CustomException {
        log.info("Entering findById(), id: {}", userId);
        if (userId <= 0) {
            throw new CustomException("User Id Must be Greater Than Zero.", HttpStatus.BAD_REQUEST);
        }

        log.info("Leaving findById()");
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("The User not Found", HttpStatus.NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public User save(User user) {
        log.info("Entering save()");
        Set<ConstraintViolation<User>> constraintViolationSet = fieldValidator.validate(user);
        ValidationException.handlingException(constraintViolationSet);
        if (user.getRoleId() <= 0) {
            throw new CustomException("Role Id Must be Greater Than Zero.", HttpStatus.BAD_REQUEST);
        }

        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new CustomException("Invalid role", HttpStatus.BAD_REQUEST));
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

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void update(Long userId, User updatedUser) {
        log.info("Entering update()");
        Set<ConstraintViolation<User>> constraintViolationSet = fieldValidator.validate(updatedUser);
        ValidationException.handlingException(constraintViolationSet);
        User user = findById(userId);
        user.setUserName(updatedUser.getUserName());
        user.setPassword(updatedUser.getPassword());
        user.setName(updatedUser.getName());
        user.setModifiedOn(Instant.now());
        userRepository.save(user);
        log.info("Leaving update()");
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(User user) {
        log.info("Entering delete()");
        userRepository.delete(user);
        log.info("Leaving delete()");
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteById(Long userId) {
        log.info("Entering deleteById()");
        User user = findById(userId);
        List<UserRoleMap> userRoles = userRoleMapRepository.findByUser(user);
        userRoles.forEach(userRoleId -> {
            userRoleMapRepository.deleteById(userRoleId.getId());
        });
        userRepository.deleteById(userId);
        log.info("Leaving deleteById()");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCustomer(long userId) {
        log.info("Entering isACustomer(), id: {}", userId);
        User checkedUser = findById(userId);
        boolean userCheck = checkUser(checkedUser, String.valueOf(UserRole.CUSTOMER));
        log.info("Leaving isACustomer()");
        return userCheck;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCafeteriaStaff(long userId) {
        log.info("Entering isACafeteriaStaff(), id: {}", userId);
        User checkedUser = findById(userId);
        boolean userCheck = checkUser(checkedUser, String.valueOf(UserRole.CAFETERIASTAFF));
        log.info("Leaving isACafeteriaStaff()");
        return userCheck;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdmin(long userId) {
        log.info("Entering isAAdmin(), id: {}", userId);
        User checkedUser = findById(userId);
        boolean userCheck = checkUser(checkedUser, String.valueOf(UserRole.ADMIN));
        log.info("Leaving isAAdmin()");
        return userCheck;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDeliveryStaff(long userId) {
        log.info("Entering isADeliveryStaff(), id: {}", userId);
        User checkedUser = findById(userId);
        boolean userCheck = checkUser(checkedUser,String.valueOf(UserRole.DELIVERYSTAFF));
        log.info("Leaving isADeliveryStaff()");
        return userCheck;
    }

    /**
     * Checks if a given user has a specific role status.
     * @param  checkedUser The user to be checked.
     * @param  status      The role status to be checked.
     * @return             `true` if the user has the specified role status, `false`
     *                     otherwise.
     */
    private boolean checkUser(User checkedUser, String status) {
        boolean userCheck = false;
        List<UserRoleMap> listOfUserRoleMap = userRoleMapRepository.findByUser(checkedUser);
        for (var userRoleMap : listOfUserRoleMap) {
            Role role = userRoleMap.getRole();
            if (role.getName().equals(status)) {
                userCheck = true;
                break;
            }

        }

        return userCheck;
    }

}
