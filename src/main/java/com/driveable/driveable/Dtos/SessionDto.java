package com.driveable.driveable.Dtos;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SessionDto {
  // add validation through annotations
  @NotNull(message = "Scenario ID cannot be null")
  @NotEmpty(message = "Scenario ID cannot be empty")
  @Positive(message = "Scenario ID must be a positive integer")
  private Integer scenarioId;

  @NotNull(message = "Date cannot be null")
  @NotEmpty(message = "Date cannot be empty")
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @Positive(message = "Date must be a valid date")
  private Date date;

  @NotNull(message = "Max participants cannot be null")
  @NotEmpty(message = "Max participants cannot be empty")
  @Positive(message = "Max participants must be a positive integer")
  @Min(value = 1, message = "Max participants must be at least 1")
  private Integer maxParticipants;

  @NotNull(message = "Location cannot be null")
  @NotEmpty(message = "Location cannot be empty")
  private String location;

  public Integer getScenarioId() {
    return scenarioId;
  }

  public void setScenarioId(Integer scenarioId) {
    this.scenarioId = scenarioId;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Integer getMaxParticipants() {
    return maxParticipants;
  }

  public void setMaxParticipants(Integer maxParticipants) {
    this.maxParticipants = maxParticipants;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
