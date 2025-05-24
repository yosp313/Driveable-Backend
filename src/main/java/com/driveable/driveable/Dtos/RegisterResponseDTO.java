
package com.driveable.driveable.Dtos;

import lombok.Data;

@Data
public class RegisterResponseDTO {
  private String token;
  private long expiresIn;

  public RegisterResponseDTO(long expiresIn, String token) {
    this.expiresIn = expiresIn;
    this.token = token;
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
}
