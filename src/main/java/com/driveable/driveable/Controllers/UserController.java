package com.driveable.driveable.Controllers;

import com.driveable.driveable.Dtos.UserNameDTO;
import com.driveable.driveable.Models.User;
import com.driveable.driveable.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/v1/user")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/name")
    public ResponseEntity<User> updateUsername(@RequestBody UserNameDTO userNameDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.updateUserName(user.getId(), userNameDTO.getFirstName(), userNameDTO.getLastName());

        return ResponseEntity.ok(user);
    }
}
