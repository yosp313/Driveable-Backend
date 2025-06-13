package com.driveable.driveable.Services;

import com.driveable.driveable.Dtos.SessionDto;
import com.driveable.driveable.Models.Scenario;
import com.driveable.driveable.Models.Session;
import com.driveable.driveable.Repositories.ScenarioRepository;
import com.driveable.driveable.Repositories.SessionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SessionService {
  private final SessionRepository sessionRepository;
  private final ScenarioRepository scenarioRepository;

  @Autowired
  public SessionService(SessionRepository sessionRepository, ScenarioRepository scenarioRepository) {
    this.sessionRepository = sessionRepository;
    this.scenarioRepository = scenarioRepository;
  }

  public List<Session> findAll() {
    return sessionRepository.findAll();
  }

  public Session findSessionById(Long id) {
    Optional<Session> session = sessionRepository.findById(id);
    return session.orElse(null);
  }

  public Session createSession(Session session) {
    sessionRepository.save(session);
    return session;
  }

  public Session createSessionFromDto(SessionDto sessDto) {
    Scenario scenario = scenarioRepository.findById(sessDto.getScenarioId())
        .orElse(null);
    if (scenario == null) {
      throw new IllegalArgumentException("Scenario with ID " + sessDto.getScenarioId() + " does not exist.");
    }

    Session session = new Session(
        scenario,
        sessDto.getDate(),
        sessDto.getMaxParticipants(),
        sessDto.getLocation());

    System.out.println("Session: " + sessDto.getDate() + " at " + sessDto.getLocation() + " is available.");

    sessionRepository.save(session);

    return session;
  }

  public void deleteSession(Long id) {
    sessionRepository.deleteById(id);
  }

  public Session updateSession(Session session) {
    sessionRepository.save(session);
    return session;
  }

  public List<Session> searchForASession(String searchText) {
    Optional<List<Session>> sessions = sessionRepository.searchForASession(searchText);
    return sessions.orElse(null);
  }

  public List<Session> findAvailableSessions() {
    Optional<List<Session>> sessions = sessionRepository.findAvailableSessions();
    return sessions.orElse(null);
  }
}
