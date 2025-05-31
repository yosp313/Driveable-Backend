package com.driveable.driveable.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.driveable.driveable.Models.Registration;
import com.driveable.driveable.Models.Session;
import com.driveable.driveable.Models.User;
import com.driveable.driveable.Repositories.RegistrationRepository;

@Service
@Transactional
public class RegistrationService {
  private RegistrationRepository regRepo;

  @Autowired
  public RegistrationService(RegistrationRepository regRepo) {
    this.regRepo = regRepo;
  }

  public Registration registerSession(Session session, User user) {
    Registration registration = new Registration();

    registration.setSession(session);
    registration.setUser(user);

    try {
      regRepo.save(registration);
      return registration;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public List<Registration> findAll(User user) {
    Long userId = user.getId();
    return regRepo.findByUserId(userId);
  }

  public List<Registration> findAll() {
    return regRepo.findAll();
  }

  public Registration findRegistrationById(Long id) {
    return regRepo.findById(id).orElse(null);
  }

  public void deleteRegistrationById(Long id) {
    regRepo.deleteById(id);
  }
}
