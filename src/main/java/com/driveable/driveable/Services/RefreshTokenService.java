package com.driveable.driveable.Services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.driveable.driveable.Models.RefreshToken;
import com.driveable.driveable.Models.User;
import com.driveable.driveable.Repositories.RefreshTokenRepository;

@Service
public class RefreshTokenService {

  @Value("${security.jwt.refresh-expiration-ms}")
  private Long refreshExpirationMs;

  private final RefreshTokenRepository refreshTokenRepository;

  public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
  }

  public RefreshToken createRefreshToken(User user) {
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setUser(user);
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshExpirationMs));
    refreshToken.setToken(UUID.randomUUID().toString());

    return refreshTokenRepository.save(refreshToken);
  }

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public void deleteByUser(User user) {
    refreshTokenRepository.deleteByUser(user);
  }
}
