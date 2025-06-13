package com.driveable.driveable.Models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
  private String location;

  @OneToOne(mappedBy = "session", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  @JsonIgnore
  private Registration registrations;

  public Session() {
  }

  public Session(String location, Date date, String feedback, float score, Scenario scenario,
      Long id) {
    this.location = location;
    this.date = date;
    this.scenario = scenario;
    this.id = id;
    this.isAvailable = true;
  }

  public Session(Scenario scenario, float score, String feedback, Date date, String location) {
    this.scenario = scenario;
    this.date = date;
    this.location = location;
    this.isAvailable = true;
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

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean isAvailable) {
    this.isAvailable = isAvailable;
  }
}
