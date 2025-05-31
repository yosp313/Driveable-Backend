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
  private String location;

  @OneToMany(mappedBy = "session", fetch = FetchType.LAZY, orphanRemoval = true)
  @JsonIgnore
  private List<Registration> registrations;

  public Session() {
  }

  public Session(String location, Date date, String feedback, float score, Scenario scenario,
      Long id) {
    this.location = location;
    this.date = date;
    this.scenario = scenario;
    this.id = id;
  }

  public Session(Scenario scenario, float score, String feedback, Date date, String location) {
    this.scenario = scenario;
    this.date = date;
    this.location = location;
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

}
