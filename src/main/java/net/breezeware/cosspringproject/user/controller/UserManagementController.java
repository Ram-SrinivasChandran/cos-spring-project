package net.breezeware.cosspringproject.user.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserManagementController {
    private final UserService userService;
    @GetMapping
    public List<User> getUserList() {
        return userService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.save(user);
    }

    @GetMapping("/{user-id}")
    public User getUserById(@PathVariable(name = "user-id",required = true) Long userId) throws CustomException {
        return userService.findById(userId);
    }

    @DeleteMapping("/{user-id}")
    public void deleteUser(@PathVariable(name = "user-id",required = true) Long userId) {
        userService.deleteById(userId);
    }

    @PutMapping("/{user-id}")
    public void updateUser(@PathVariable(name = "user-id",required = true) Long userId, @RequestBody User user) {
        userService.update(userId, user);
    }
}
