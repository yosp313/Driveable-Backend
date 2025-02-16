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

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @Column(nullable = false)
    private float score;

    @Column(nullable = false)
    private String feedback;

    @Column (nullable = false)
    private Date date;

    @Column (nullable = false)
    private String location;

    @Column(nullable = false)
    private boolean isRegistered;

    public Session() {}

    public Session(String location, Date date, String feedback, float score, Scenario scenario,boolean isRegistered,User user, Long id) {
        this.location = location;
        this.date = date;
        this.feedback = feedback;
        this.score = score;
        this.scenario = scenario;
        this.isRegistered = isRegistered;
        this.user = user;
        this.id = id;
    }

    public Session(Scenario scenario,User user, float score, String feedback, Date date, String location, boolean isRegistered) {
        this.scenario = scenario;
        this.user = user;
        this.score = score;
        this.feedback = feedback;
        this.date = date;
        this.location = location;
        this.isRegistered = isRegistered;
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

    public boolean getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}