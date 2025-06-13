package com.driveable.driveable.Dtos;

import com.driveable.driveable.Models.Role;
import com.driveable.driveable.Models.TransmissionType;
import lombok.Data;

@Data
public class RegisterUserDto {
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private int age;
  private TransmissionType transmissionType;
  private Role role = Role.USER;

  public RegisterUserDto(String email, String password, String firstName, String lastName, int age,
      TransmissionType transmissionType) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.transmissionType = transmissionType;
    this.role = Role.USER;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public TransmissionType getTransmissionType() {
    return transmissionType;
  }

  public void setTransmissionType(TransmissionType transmissionType) {
    this.transmissionType = transmissionType;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
