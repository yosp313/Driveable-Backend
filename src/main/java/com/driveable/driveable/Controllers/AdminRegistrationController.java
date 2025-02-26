package com.driveable.driveable.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.driveable.driveable.Models.Registration;
import com.driveable.driveable.Services.RegistrationService;
import com.driveable.driveable.Utils.CustomError;

@RestController
@RequestMapping("api/v1/admin-dashboard/registrations")
public class AdminRegistrationController {
  private final RegistrationService registrationService;

  @Autowired
  public AdminRegistrationController(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }

  @GetMapping
  public ResponseEntity<?> getAll() {
    List<Registration> registrations = registrationService.findAll();

    if (registrations == null || registrations.isEmpty()) {
      CustomError err = new CustomError(404, "Registrations not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    return ResponseEntity.ok(registrations);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getRegistrationById(@PathVariable Long id) {
    Registration registration = registrationService.findRegistrationById(id);

    if (registration == null) {
      CustomError err = new CustomError(HttpStatus.NOT_FOUND.value(), "Registration not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    return ResponseEntity.ok(registration);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteRegistrationById(@PathVariable Long id) {
    Registration registration = registrationService.findRegistrationById(id);

    if (registration == null) {
      CustomError err = new CustomError(HttpStatus.NOT_FOUND.value(), "Registration not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    registrationService.deleteRegistrationById(id);
    return ResponseEntity.ok("Registration deleted");
  }

}
