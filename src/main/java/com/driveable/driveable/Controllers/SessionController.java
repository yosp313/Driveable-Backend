package com.driveable.driveable.Controllers;

import com.driveable.driveable.Models.Registration;
import com.driveable.driveable.Models.Session;
import com.driveable.driveable.Models.User;
import com.driveable.driveable.Services.RegistrationService;
import com.driveable.driveable.Services.SessionService;
import com.driveable.driveable.Utils.CustomError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  @GetMapping("/search")
  public ResponseEntity<?> SearchSessions(@RequestParam String query) {
    List<Session> sessions = sessionService.searchForASession(query);

    if (sessions == null) {
      CustomError err = new CustomError(HttpStatus.NOT_FOUND.value(), "No sessions found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    return ResponseEntity.ok(sessions);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> GetSessionById(@PathVariable Long id) {
    Session sess = sessionService.findSessionById(id);

    if (sess == null) {
      CustomError err = new CustomError(HttpStatus.NOT_FOUND.value(), "Session not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    return ResponseEntity.ok(sess);
  }

  @PostMapping("/{id}/register")
  public ResponseEntity<?> RegisterSession(@PathVariable Long id) {
    Session session = sessionService.findSessionById(id);

    if (session == null) {
      CustomError err = new CustomError(HttpStatus.NOT_FOUND.value(), "Session not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Registration registration = registrationService.registerSession(session, user);

    if (registration == null) {
      CustomError err = new CustomError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to register session");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(registration);
  }

}
