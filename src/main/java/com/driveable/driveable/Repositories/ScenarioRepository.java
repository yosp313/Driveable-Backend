package com.driveable.driveable.Repositories;

import com.driveable.driveable.Models.Scenario;
import com.driveable.driveable.Models.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScenarioRepository extends JpaRepository<Scenario, Integer> {
    
    List<Scenario> findByDifficulty(Difficulty difficulty);
    
    List<Scenario> findByEnvironmentType(String environmentType);
    
    Optional<Scenario> findByName(String name);
    
    @Query("SELECT s FROM Scenario s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR LOWER(s.environmentType) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<Scenario> searchScenarios(@Param("searchText") String searchText);
    
    List<Scenario> findByDifficultyAndEnvironmentType(Difficulty difficulty, String environmentType);
}