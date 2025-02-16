package com.driveable.driveable.Models;
import jakarta.persistence.*;

@Entity
@Table(name = "scenarios")
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer scenarioID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String environmentType;

    @Column(nullable = false)
    private Difficulty  difficulty;

    public Scenario() {}

    public Scenario(Integer scenarioID, Difficulty difficulty, String environmentType, String name) {
        this.scenarioID = scenarioID;
        this.difficulty = difficulty;
        this.environmentType = environmentType;
        this.name = name;
    }

    public Scenario(String name, String environmentType, Difficulty difficulty) {
        this.name = name;
        this.environmentType = environmentType;
        this.difficulty = difficulty;
    }

    public Integer getScenarioID() {
        return scenarioID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getEnvironmentType() {
        return environmentType;
    }

    public void setEnvironmentType(String environmentType) {
        this.environmentType = environmentType;
    }
}
