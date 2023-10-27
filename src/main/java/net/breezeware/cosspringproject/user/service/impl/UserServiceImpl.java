package net.breezeware.cosspringproject.user.service.impl;

import lombok.RequiredArgsConstructor;
import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.exception.ValidationException;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.user.dao.RoleRepository;
import net.breezeware.cosspringproject.user.dao.UserRepository;
import net.breezeware.cosspringproject.user.dao.UserRoleMapRepository;
import net.breezeware.cosspringproject.user.entity.Role;
import net.breezeware.cosspringproject.user.entity.User;
import net.breezeware.cosspringproject.user.entity.UserRoleMap;
import net.breezeware.cosspringproject.user.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final RoleRepository roleRepository;

//    @Autowired
//    private  Validator fieldValidator;

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    public User findById(Long aLong) {
        return userRepository.findById(aLong).orElseThrow(()->new CustomException("Enter a Valid User Id",HttpStatus.BAD_REQUEST));
    }

    @Override
    public User save(User user) {
//        Set<ConstraintViolation<User>> constraintViolationSet = fieldValidator.validate(object);
//        ValidationException.handlingException(constraintViolationSet);
        if(user.getRoleId()<=0){
            throw new CustomException("Role Id Must be Greater Than Zero.",HttpStatus.BAD_REQUEST);
        }
        Role role = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new CustomException("Invalid role", HttpStatus.BAD_REQUEST));
        user.setCreatedOn(Instant.now());
        user.setModifiedOn(Instant.now());
        User savedUser = userRepository.save(user);
        UserRoleMap userRoleMap=new UserRoleMap();
        userRoleMap.setRoleId(role);
        userRoleMap.setUserId(savedUser);
        userRoleMap.setCreatedOn(Instant.now());
        userRoleMap.setModifiedOn(Instant.now());
        userRoleMapRepository.save(userRoleMap);
        return savedUser;
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteById(Long aLong) {
        userRepository.deleteById(aLong);
    }
}
