package com.driveable.driveable.Models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "sessions")
public class Session {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, unique = true)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "ScenarioID", nullable = false) // Foreign Key to Scenario
  private Scenario scenario;

  @Column(nullable = false)
  private Date date;

  @Column(nullable = false)
  private boolean isAvailable = true;

  @Column(nullable = false)
  private Integer maxParticipants;
  @Column(nullable = false)
  private Integer participantsCount = 0;

  @Column(nullable = false)
  private String location;

  @OneToMany(mappedBy = "session", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Registration> registrations;

  public Session() {
  }
  public Session(Scenario scenario, Date date, Integer maxParticipants,
      String location, List<Registration> registrations) {
    this.scenario = scenario;
    this.date = date;
    this.isAvailable = true;
    this.maxParticipants = maxParticipants;
    this.participantsCount = 0;
    this.location = location;
    this.registrations = registrations;
  }
  public Session(Scenario scenario, Date date, Integer maxParticipants,
      String location) {
    this.scenario = scenario;
    this.date = date;
    this.isAvailable = true;
    this.maxParticipants = maxParticipants;
    this.participantsCount = 0;
    this.location = location;
  }
  public Session(Long id, Scenario scenario, Date date, Integer maxParticipants, String location,
      List<Registration> registrations) {
    this.id = id;
    this.scenario = scenario;
    this.date = date;
    this.isAvailable = true;
    this.maxParticipants = maxParticipants;
    this.participantsCount = 0;
    this.location = location;
    this.registrations = registrations;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Scenario getScenario() {
    return scenario;
  }

  public void setScenario(Scenario scenario) {
    this.scenario = scenario;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean isAvailable) {
    this.isAvailable = isAvailable;
  }

  public Integer getMaxParticipants() {
    return maxParticipants;
  }

  public void setMaxParticipants(Integer maxParticipants) {
    this.maxParticipants = maxParticipants;
  }
  public Integer getParticipantsCount() {
    return participantsCount;
  }

  public void setParticipantsCount(Integer participantsCount) {
    this.participantsCount = participantsCount;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
  public List<Registration> getRegistrations() {
    return registrations;
  }

  public void setRegistrations(List<Registration> registrations) {
    this.registrations = registrations;
  }

}
