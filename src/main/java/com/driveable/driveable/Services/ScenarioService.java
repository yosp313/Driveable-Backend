package com.driveable.driveable.Services;

import com.driveable.driveable.Models.Difficulty;
import com.driveable.driveable.Models.Scenario;
import com.driveable.driveable.Repositories.ScenarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScenarioService {

  private final ScenarioRepository scenarioRepository;

  @Autowired
  public ScenarioService(ScenarioRepository scenarioRepository) {
    this.scenarioRepository = scenarioRepository;
  }

  public List<Scenario> findAll() {
    return scenarioRepository.findAll();
  }

  public Scenario findScenarioById(Integer id) {
    Optional<Scenario> scenario = scenarioRepository.findById(id);
    return scenario.orElse(null);
  }

  public Scenario createScenario(Scenario scenario) {
    return scenarioRepository.save(scenario);
  }

  public void deleteScenario(Integer id) {
    scenarioRepository.deleteById(id);
  }

  public Scenario updateScenario(Scenario scenario) {
    return scenarioRepository.save(scenario);
  }

  public List<Scenario> findByDifficulty(Difficulty difficulty) {
    return scenarioRepository.findByDifficulty(difficulty);
  }

  public List<Scenario> findByEnvironmentType(String environmentType) {
    return scenarioRepository.findByEnvironmentType(environmentType);
  }

  public Scenario findByName(String name) {
    Optional<Scenario> scenario = scenarioRepository.findByName(name);
    return scenario.orElse(null);
  }

  public List<Scenario> searchScenarios(String searchText) {
    return scenarioRepository.searchScenarios(searchText);
  }

  public List<Scenario> findByDifficultyAndEnvironmentType(Difficulty difficulty, String environmentType) {
    return scenarioRepository.findByDifficultyAndEnvironmentType(difficulty, environmentType);
  }
}
