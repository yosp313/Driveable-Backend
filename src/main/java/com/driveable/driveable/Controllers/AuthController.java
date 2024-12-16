package com.driveable.driveable.Controllers;

import com.driveable.driveable.Dtos.LoginResponseDto;
import com.driveable.driveable.Dtos.LoginUserDto;
import com.driveable.driveable.Dtos.RegisterUserDto;
import com.driveable.driveable.Models.User;
import com.driveable.driveable.Services.AuthService;
import com.driveable.driveable.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/auth")
@RestController
public class AuthController {
    private final JwtService jwtService;
    private final AuthService authService;

    @Autowired
    public AuthController(JwtService jwtService, AuthService authService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto){
        User registerdUser = authService.signup(registerUserDto);

        return ResponseEntity.ok(registerdUser);
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginUserDto loginUserDto){
        User authenticatedUser = authService.login(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDto loginResponseDto = new LoginResponseDto(jwtService.getExpirationTime(), jwtToken);

        return ResponseEntity.ok(loginResponseDto);
    }
}
