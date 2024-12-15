package com.driveable.driveable.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private int age;
    @Transient
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private HandicapType handicapType;
    @Column(nullable = false)
    private TransmissionType transmissionType;
    @Column(nullable = false)
    private Role role;

    public User(){}

    public User(Long id, String firstName, String lastName, int age, String email, String password, HandicapType handicapType, TransmissionType transmissionType, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.handicapType = handicapType;
        this.transmissionType = transmissionType;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public HandicapType getHandicapType() {
        return handicapType;
    }

    public void setHandicapType(HandicapType handicapType) {
        this.handicapType = handicapType;
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

enum HandicapType{
    NONE
}

enum TransmissionType{
    MANUAL,
    AUTOMATIC
}

enum Role{
    ADMIN,
    USER,
    INSTRUCTOR
}
