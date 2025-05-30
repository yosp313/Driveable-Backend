package com.driveable.driveable.Factories;

import com.driveable.driveable.Models.Difficulty;
import com.driveable.driveable.Models.Scenario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ScenarioFactory {
    
    private final Random random = new Random();
    
    private final String[] scenarioNames = {
        "City Center Navigation", "Highway Merge", "Parallel Parking", "Three-Point Turn",
        "Roundabout Navigation", "Night Driving", "Rain Driving", "Snow Conditions",
        "Emergency Braking", "Pedestrian Crossing", "School Zone", "Construction Zone",
        "Mountain Roads", "Rural Driving", "Reverse Parking", "Lane Changing",
        "Traffic Light Response", "Stop Sign Practice", "Intersection Navigation", "Bridge Crossing"
    };
    
    private final String[] environmentTypes = {
        "Urban", "Suburban", "Highway", "Rural", "Mountain", "Coastal", 
        "Industrial", "Residential", "Commercial", "Mixed"
    };
    
    public Scenario createScenario() {
        String name = scenarioNames[random.nextInt(scenarioNames.length)];
        String environmentType = environmentTypes[random.nextInt(environmentTypes.length)];
        Difficulty difficulty = Difficulty.values()[random.nextInt(Difficulty.values().length)];
        
        return new Scenario(name, environmentType, difficulty);
    }
    
    public Scenario createScenario(Difficulty difficulty) {
        String name = scenarioNames[random.nextInt(scenarioNames.length)];
        String environmentType = environmentTypes[random.nextInt(environmentTypes.length)];
        
        return new Scenario(name, environmentType, difficulty);
    }
    
    public Scenario createScenarioWithSpecificData(String name, String environmentType, Difficulty difficulty) {
        return new Scenario(name, environmentType, difficulty);
    }
    
    public List<Scenario> createScenarios(int count) {
        List<Scenario> scenarios = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            scenarios.add(createScenario());
        }
        return scenarios;
    }
    
    public List<Scenario> createScenariosWithDifficulty(int easyCount, int mediumCount, int hardCount) {
        List<Scenario> scenarios = new ArrayList<>();
        
        for (int i = 0; i < easyCount; i++) {
            scenarios.add(createScenario(Difficulty.EASY));
        }
        
        for (int i = 0; i < mediumCount; i++) {
            scenarios.add(createScenario(Difficulty.MEDIUM));
        }
        
        for (int i = 0; i < hardCount; i++) {
            scenarios.add(createScenario(Difficulty.HARD));
        }
        
        return scenarios;
    }
    
    public List<Scenario> createDefaultScenarios() {
        List<Scenario> scenarios = new ArrayList<>();
        
        scenarios.add(new Scenario("Basic City Driving", "Urban", Difficulty.EASY));
        scenarios.add(new Scenario("Highway Entry", "Highway", Difficulty.EASY));
        scenarios.add(new Scenario("Parking Lot Practice", "Commercial", Difficulty.EASY));
        scenarios.add(new Scenario("Residential Streets", "Residential", Difficulty.EASY));
        
        scenarios.add(new Scenario("Rush Hour Traffic", "Urban", Difficulty.MEDIUM));
        scenarios.add(new Scenario("Complex Intersections", "Mixed", Difficulty.MEDIUM));
        scenarios.add(new Scenario("Highway Merge", "Highway", Difficulty.MEDIUM));
        scenarios.add(new Scenario("Parallel Parking", "Urban", Difficulty.MEDIUM));
        
        scenarios.add(new Scenario("Night Driving", "Mixed", Difficulty.HARD));
        scenarios.add(new Scenario("Adverse Weather", "Mountain", Difficulty.HARD));
        scenarios.add(new Scenario("Emergency Scenarios", "Highway", Difficulty.HARD));
        scenarios.add(new Scenario("Complex Navigation", "Urban", Difficulty.HARD));
        
        return scenarios;
    }
}