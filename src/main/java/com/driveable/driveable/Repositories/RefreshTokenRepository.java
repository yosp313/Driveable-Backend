package com.driveable.driveable.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.driveable.driveable.Models.RefreshToken;
import com.driveable.driveable.Models.User;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByToken(String token);

  @Transactional
  @Modifying
  @Query("DELETE FROM RefreshToken rt WHERE rt.user = :user")
  void deleteByUser(User user);
}
