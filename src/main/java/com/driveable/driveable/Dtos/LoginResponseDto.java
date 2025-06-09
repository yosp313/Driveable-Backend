package com.driveable.driveable.Dtos;

import java.time.Instant;

import lombok.Data;

@Data
public class LoginResponseDto {
  private String token;
  private long expiresIn;
  private Instant refreshExpirationTime;
  private String refreshToken;

  public LoginResponseDto(String token, long expiresIn, Instant refreshExpirationTime, String refreshToken) {
    this.token = token;
    this.expiresIn = expiresIn;
    this.refreshExpirationTime = refreshExpirationTime;
    this.refreshToken = refreshToken;
  }

  public LoginResponseDto() {
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public long getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(long expiresIn) {
    this.expiresIn = expiresIn;
  }

  public Instant getRefreshExpirationTime() {
    return refreshExpirationTime;
  }

  public void setRefreshExpirationTime(Instant refreshExpirationTime) {
    this.refreshExpirationTime = refreshExpirationTime;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

}
