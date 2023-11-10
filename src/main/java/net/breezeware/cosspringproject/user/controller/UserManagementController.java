package net.breezeware.cosspringproject.user.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.user.entity.User;
import net.breezeware.cosspringproject.user.service.api.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserManagementController {

    private final UserService userService;

    @GetMapping
    public List<User> retrieveUsers() {
        log.info("Entering retrieveUsers()");
        List<User> users = userService.findAll();
        log.info("Leaving retrieveUsers()");
        return users;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createUser(@RequestBody User user) {
        log.info("Entering createUser()");
        userService.save(user);
        log.info("Leaving createUser()");
    }

    @GetMapping("/{user-id}")
    public User retrieveUser(@PathVariable(name = "user-id", required = true) Long userId) throws CustomException {
        log.info("Entering retrieveUser()");
        User user = userService.findById(userId);
        log.info("Leaving retrieveUser()");
        return user;
    }

    @DeleteMapping("/{user-id}")
    public void deleteUser(@PathVariable(name = "user-id", required = true) Long userId) {
        log.info("Entering deleteUser()");
        userService.deleteById(userId);
        log.info("Leaving deleteUser()");
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{user-id}")
    public void updateUser(@PathVariable(name = "user-id", required = true) Long userId, @RequestBody User user) {
        log.info("Entering updateUser()");
        userService.update(userId, user);
        log.info("Leaving updateUser()");
    }
}
