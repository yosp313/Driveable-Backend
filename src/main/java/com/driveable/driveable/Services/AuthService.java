package com.driveable.driveable.Services;

import com.driveable.driveable.Dtos.LoginUserDto;
import com.driveable.driveable.Dtos.RegisterUserDto;
import com.driveable.driveable.Models.User;
import com.driveable.driveable.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signup(RegisterUserDto input){
        User user = new User(
                input.getFirstName(),
                input.getLastName(),
                input.getAge(),
                input.getEmail(),
                passwordEncoder.encode(input.getPassword()),
                input.getHandicapType(),
                input.getTransmissionType(),
                input.getRole()
        );

        return userRepository.save(user);
    }

    public User login(LoginUserDto input){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
