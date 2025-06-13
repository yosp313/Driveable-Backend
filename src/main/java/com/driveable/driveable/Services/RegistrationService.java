package com.driveable.driveable.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.driveable.driveable.Models.Registration;
import com.driveable.driveable.Models.Session;
import com.driveable.driveable.Models.User;
import com.driveable.driveable.Repositories.RegistrationRepository;
import com.driveable.driveable.Repositories.SessionRepository;

@Service
@Transactional
public class RegistrationService {
  private RegistrationRepository regRepo;
  private SessionRepository sessRepo;

  @Autowired
  public RegistrationService(RegistrationRepository regRepo, SessionRepository sessRepo) {
    this.regRepo = regRepo;
    this.sessRepo = sessRepo;
  }

  public Registration registerSession(Session session, User user) {
    Registration registration = new Registration();

    session.setAvailable(false);

    registration.setSession(session);
    registration.setUser(user);

    try {
      regRepo.save(registration);
      sessRepo.save(session);
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

  @Transactional
  public void deleteRegistration(Registration reg) {
    if (reg == null) {
      throw new IllegalArgumentException("Registration cannot be null");
    }
    // Get the associated session before deleting
    Long sessionId = reg.getSession().getId();
    Session session = sessRepo.findById(sessionId).orElse(null);
    System.out.println("Session with ID: " + reg.getSession().getId());

    // Delete the registration first
    regRepo.delete(reg);

    // Make the session available again
    if (session != null) {
      session.setAvailable(true);
      sessRepo.save(session);
    }

  }
}
