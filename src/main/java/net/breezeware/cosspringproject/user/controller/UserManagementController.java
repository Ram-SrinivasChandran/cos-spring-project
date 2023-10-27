package net.breezeware.cosspringproject.user.controller;

import net.breezeware.cosspringproject.user.entity.User;
import net.breezeware.cosspringproject.user.service.api.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserManagementController {
    private final UserService userService;
    public UserManagementController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<User> getUserList(){
        return userService.findAll();
    }
    @PostMapping
    public void createUser(@RequestBody User user){
        userService.save(user);
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.findById(id);
    }
    @DeleteMapping
    public void deleteUser(@RequestBody User user){
        userService.delete(user);
    }

}
