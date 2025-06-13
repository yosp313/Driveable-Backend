package com.driveable.driveable.Controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.driveable.driveable.Models.Registration;
import com.driveable.driveable.Models.User;
import com.driveable.driveable.Services.RegistrationService;
import com.driveable.driveable.Utils.CustomError;

@RestController
@RequestMapping("api/v1/registrations")
public class RegisterationController {
  private final RegistrationService registrationService;

  @Autowired
  public RegisterationController(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }

  @GetMapping
  public ResponseEntity<?> GetAllRegistrations(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok(registrationService.findAll(user));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> GetRegistrationById(@PathVariable Long id) {
    Registration reg = registrationService.findRegistrationById(id);

    if (reg == null) {
      CustomError err = new CustomError(HttpStatus.NOT_FOUND.value(), "Registration not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
    return ResponseEntity.ok(reg);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> DeleteRegistrationById(@PathVariable Long id, @AuthenticationPrincipal User user) {
    Registration reg = registrationService.findRegistrationById(id);

    if (reg == null) {
      CustomError err = new CustomError(HttpStatus.NOT_FOUND.value(), "Registration not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    if (!reg.getUser().getId().equals(user.getId())) {
      CustomError err = new CustomError(HttpStatus.FORBIDDEN.value(),
          "You are not allowed to delete this registration");
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    try {
      registrationService.deleteRegistration(reg);
      return ResponseEntity.ok(Collections.singletonMap("message", "Registration deleted successfully"));
    } catch (Exception e) {
      CustomError err = new CustomError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete registration");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
  }

}
