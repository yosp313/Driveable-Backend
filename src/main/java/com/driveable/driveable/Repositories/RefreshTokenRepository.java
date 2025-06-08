package com.driveable.driveable.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.driveable.driveable.Models.RefreshToken;
import com.driveable.driveable.Models.User;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
  public Optional<RefreshToken> findByToken(String token);

  public Optional<RefreshToken> deleteByUser(User user);
}
