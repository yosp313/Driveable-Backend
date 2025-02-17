package com.driveable.driveable.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driveable.driveable.Models.Registration;
import com.driveable.driveable.Models.Session;
import com.driveable.driveable.Models.User;
import com.driveable.driveable.Repositories.RegistrationRepository;

@Service
public class RegistrationService {
  private RegistrationRepository repository;

  @Autowired
  public RegistrationService(RegistrationRepository repository) {
    this.repository = repository;
  }

  public Registration registerSession(Session session, User user) {
    Registration registration = new Registration();

    registration.setSession(session);
    registration.setUser(user);

    try {
      repository.save(registration);
      return registration;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
