package com.driveable.driveable.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.driveable.driveable.Models.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}
