package com.driveable.driveable.Controllers;

import com.driveable.driveable.Models.User;
import com.driveable.driveable.Services.UserService;
import jakarta.websocket.server.PathParam;
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

        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> GetUserById(@PathVariable Long id){
        User user = userService.findUserById(id);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/name/{id}")
    public ResponseEntity<User> UpdateUserName(@PathVariable Long id, @RequestParam String firstName, @RequestParam String lastName){
        User user = userService.updateUserName(id, firstName, lastName);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> DeleteUser(@PathVariable Long id){
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
