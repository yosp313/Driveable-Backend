package com.driveable.driveable.Factories;

import com.driveable.driveable.Models.Role;
import com.driveable.driveable.Models.TransmissionType;
import com.driveable.driveable.Models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class UserFactory {

  private final PasswordEncoder passwordEncoder;
  private final Random random = new Random();

  private final String[] firstNames = {
      "John", "Jane", "Michael", "Sarah", "David", "Emily", "Robert", "Lisa",
      "James", "Maria", "William", "Jennifer", "Richard", "Linda", "Joseph", "Patricia",
      "Thomas", "Elizabeth", "Christopher", "Barbara", "Daniel", "Susan", "Matthew", "Jessica"
  };

  private final String[] lastNames = {
      "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis",
      "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas",
      "Taylor", "Moore", "Jackson", "Martin", "Lee", "Perez", "Thompson", "White"
  };

  public UserFactory(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public User createUser() {
    return createUser(Role.USER);
  }

  public User createUser(Role role) {
    String firstName = firstNames[random.nextInt(firstNames.length)];
    String lastName = lastNames[random.nextInt(lastNames.length)];
    String email = generateEmail(firstName, lastName);
    int age = random.nextInt(45) + 18; // Age between 18-62
    String password = passwordEncoder.encode("password123");
    TransmissionType transmissionType = random.nextBoolean() ? TransmissionType.AUTOMATIC : TransmissionType.MANUAL;

    return new User(firstName, lastName, age, email, password, transmissionType, role);
  }

  public User createAdmin() {
    return createUser(Role.ADMIN);
  }

  public User createUserWithSpecificData(String firstName, String lastName, String email, Role role) {
    int age = random.nextInt(45) + 18;
    String password = passwordEncoder.encode("password123");
    TransmissionType transmissionType = random.nextBoolean() ? TransmissionType.AUTOMATIC : TransmissionType.MANUAL;

    return new User(firstName, lastName, age, email, password, transmissionType, role);
  }

  public List<User> createUsers(int count) {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      users.add(createUser());
    }
    return users;
  }

  public List<User> createMixedRoleUsers(int userCount, int adminCount, int instructorCount) {
    List<User> users = new ArrayList<>();

    for (int i = 0; i < userCount; i++) {
      users.add(createUser(Role.USER));
    }

    for (int i = 0; i < adminCount; i++) {
      users.add(createAdmin());
    }

    return users;
  }

  private String generateEmail(String firstName, String lastName) {
    String[] domains = { "gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "example.com" };
    String domain = domains[random.nextInt(domains.length)];
    return firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + domain;
  }
}
