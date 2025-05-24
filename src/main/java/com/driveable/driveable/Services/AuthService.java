package com.driveable.driveable.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.driveable.driveable.Dtos.LoginUserDto;
import com.driveable.driveable.Dtos.RegisterUserDto;
import com.driveable.driveable.Models.User;
import com.driveable.driveable.Repositories.UserRepository;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  @Autowired
  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

  public User signup(RegisterUserDto input) {
    User user = new User(
        input.getFirstName(),
        input.getLastName(),
        input.getAge(),
        input.getEmail(),
        passwordEncoder.encode(input.getPassword()),
        input.getTransmissionType(),
        input.getRole());

    User isExistingUser = userRepository.findByEmail(input.getEmail())
        .orElse(null);

    if (isExistingUser != null) {
      return null;
    }

    return userRepository.save(user);
  }

  public User login(LoginUserDto input) {

    User user = userRepository.findByEmail(input.getEmail())
        .orElse(null);

    if (user == null) {
      return null;
    }

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            input.getEmail(),
            input.getPassword()));

    return user;
  }
}
