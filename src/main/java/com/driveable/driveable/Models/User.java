package com.driveable.driveable.Models;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String password;
    private HandicapType handicapType;
    private TransmissionType transmissionType;
    private Role role;
}

enum HandicapType{

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
