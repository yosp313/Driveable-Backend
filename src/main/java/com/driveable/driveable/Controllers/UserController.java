package com.driveable.driveable.Controllers;

import com.driveable.driveable.Models.User;
import com.driveable.driveable.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> GetAllUsers(){
        return userService.findAll();
    }

    @PostMapping
    public ResponseEntity<User> CreateUser(@RequestBody User user){
        User createdUser = userService.createUser(user);

        return ResponseEntity.ok(createdUser);
    }
}
