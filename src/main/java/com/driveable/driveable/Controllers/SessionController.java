package com.driveable.driveable.Controllers;

import com.driveable.driveable.Models.Registration;
import com.driveable.driveable.Models.Session;
import com.driveable.driveable.Models.User;
import com.driveable.driveable.Services.RegistrationService;
import com.driveable.driveable.Services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/sessions")
public class SessionController {
  public final SessionService sessionService;
  private final RegistrationService registrationService;

  @Autowired
  public SessionController(SessionService sessionService, RegistrationService registrationService) {
    this.sessionService = sessionService;
    this.registrationService = registrationService;
  }

  @GetMapping
  public List<Session> GetAllSessions() {
    return sessionService.findAll();
  }

  @GetMapping("/{id}")
  public Session GetSessionById(@PathVariable Long id) {
    return sessionService.findSessionById(id);
  }

  @PostMapping("/register/{id}")
  public ResponseEntity<Registration> RegisterSession(@PathVariable Long id) {
    Session session = sessionService.findSessionById(id);

    if (session == null) {
      return ResponseEntity.notFound().build();
    }

    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Registration registration = registrationService.registerSession(session, user);

    if (registration == null) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(registration);
  }

}
