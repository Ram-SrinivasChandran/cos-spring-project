package net.breezeware.cosspringproject.user.controller;

import java.util.List;

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

@RestController
@RequestMapping("/api/users")
public class UserManagementController {
    private final UserService userService;

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUserList() {
        return userService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.save(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) throws CustomException {
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.update(id, user);
    }
}
