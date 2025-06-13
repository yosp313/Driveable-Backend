package com.driveable.driveable.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.driveable.driveable.Models.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
  List<Registration> findByUserId(Long userId);
  Optional<Registration> findBySessionId(Long sessionId);
  boolean existsBySessionId(Long sessionId);
}
