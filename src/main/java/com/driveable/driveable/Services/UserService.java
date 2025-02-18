package com.driveable.driveable.Services;

import com.driveable.driveable.Models.User;
import com.driveable.driveable.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  public final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User createUser(User user) {
    userRepository.save(user);

    return user;
  }

  public void deleteUser(Long id) {
    User user = findUserById(id);

    userRepository.delete(user);
  }

  public User findUserById(Long id) {
    Optional<User> user = userRepository.findById(id);

    return user.orElse(null);
  }

  public User updateUserName(Long id, String firstName, String lastName) {
    User user = findUserById(id);

    user.setFirstName(firstName);
    user.setLastName(lastName);

    userRepository.save(user);

    return user;
  }

  // TODO: function to update the user's password and another one for the user's
  // email
}
