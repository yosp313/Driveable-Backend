package com.driveable.driveable.Models;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table (name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ScenarioID", nullable = false) // Foreign Key to Scenario
    private Scenario scenario;

    @Column(nullable = false)
    private float score;

    @Column(nullable = false)
    private String feedback;

    @Column (nullable = false)
    private Date date;

    @Column (nullable = false)
    private String location;

    public Session() {}

    public Session(String location, Date date, String feedback, float score, Scenario scenario, Long id) {
        this.location = location;
        this.date = date;
        this.feedback = feedback;
        this.score = score;
        this.scenario = scenario;
        this.id = id;
    }

    public Session(Scenario scenario, float score, String feedback, Date date, String location) {
        this.scenario = scenario;
        this.score = score;
        this.feedback = feedback;
        this.date = date;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
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