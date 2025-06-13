package com.driveable.driveable.Services;

import com.driveable.driveable.Factories.ScenarioFactory;
import com.driveable.driveable.Factories.SessionFactory;
import com.driveable.driveable.Factories.UserFactory;
import com.driveable.driveable.Models.*;
import com.driveable.driveable.Repositories.RegistrationRepository;
import com.driveable.driveable.Repositories.ScenarioRepository;
import com.driveable.driveable.Repositories.SessionRepository;
import com.driveable.driveable.Repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

@Service
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {

  private final UserRepository userRepository;
  private final ScenarioRepository scenarioRepository;
  private final SessionRepository sessionRepository;
  private final RegistrationRepository registrationRepository;

  private final UserFactory userFactory;
  private final ScenarioFactory scenarioFactory;
  private final SessionFactory sessionFactory;

  private final Random random = new Random();

  @Autowired
  public DatabaseSeeder(
      UserRepository userRepository,
      ScenarioRepository scenarioRepository,
      SessionRepository sessionRepository,
      RegistrationRepository registrationRepository,
      UserFactory userFactory,
      ScenarioFactory scenarioFactory,
      SessionFactory sessionFactory) {
    this.userRepository = userRepository;
    this.scenarioRepository = scenarioRepository;
    this.sessionRepository = sessionRepository;
    this.registrationRepository = registrationRepository;
    this.userFactory = userFactory;
    this.scenarioFactory = scenarioFactory;
    this.sessionFactory = sessionFactory;
  }

  @Override
  public void run(String... args) throws Exception {
    if (shouldSeedDatabase()) {
      log.info("Starting database seeding...");
      seedDatabase();
      log.info("Database seeding completed successfully!");
    } else {
      log.info("Database already contains data. Skipping seeding.");
    }
  }

  private boolean shouldSeedDatabase() {
    // Only seed if the database is empty
    return userRepository.count() == 0 &&
        scenarioRepository.count() == 0 &&
        sessionRepository.count() == 0;
  }

  @Transactional
  public void seedDatabase() {
    try {
      // 1. Create default admin user
      createDefaultAdminUser();

      // 2. Seed scenarios
      List<Scenario> scenarios = seedScenarios();
      log.info("Created {} scenarios", scenarios.size());

      // 3. Seed users
      List<User> users = seedUsers();
      log.info("Created {} users", users.size());

      // 4. Seed sessions
      List<Session> sessions = seedSessions(scenarios);
      log.info("Created {} sessions", sessions.size());

      // 5. Seed some sample registrations
      seedRegistrations(users, sessions);
      log.info("Created sample registrations");

    } catch (Exception e) {
      log.error("Error during database seeding: ", e);
      throw e;
    }
  }

  private void createDefaultAdminUser() {
    // Create a default admin user with known credentials
    User adminUser = userFactory.createUserWithSpecificData(
        "Admin",
        "User",
        "admin@driveable.com",
        Role.ADMIN);
    userRepository.save(adminUser);
    log.info("Created default admin user: admin@driveable.com (password: password123)");
  }

  private List<Scenario> seedScenarios() {
    // Create default scenarios with predefined content
    List<Scenario> defaultScenarios = scenarioFactory.createDefaultScenarios();
    scenarioRepository.saveAll(defaultScenarios);

    // Create additional random scenarios
    List<Scenario> randomScenarios = scenarioFactory.createScenariosWithDifficulty(5, 8, 7);
    scenarioRepository.saveAll(randomScenarios);

    defaultScenarios.addAll(randomScenarios);
    return defaultScenarios;
  }

  private List<User> seedUsers() {
    // Create mixed role users: 5 regular users, 1 admin, 0 instructors
    List<User> users = userFactory.createMixedRoleUsers(5, 1, 0);

    // Create some specific test users
    users.add(userFactory.createUserWithSpecificData("John", "Doe", "john.doe@test.com", Role.USER));
    users.add(userFactory.createUserWithSpecificData("Jane", "Smith", "jane.smith@test.com", Role.USER));

    return userRepository.saveAll(users);
  }

  private List<Session> seedSessions(List<Scenario> scenarios) {
    // Create a weekly schedule
    List<Session> weeklySessions = sessionFactory.createWeeklySchedule(scenarios);

    // Create sessions for the next 14 days
    List<Session> futureSessions = sessionFactory.createSessionsForNextDays(scenarios, 14);

    weeklySessions.addAll(futureSessions);
    return sessionRepository.saveAll(weeklySessions);
  }

  @Transactional
  private void seedRegistrations(List<User> users, List<Session> sessions) {
    // Filter to get only regular users
    List<User> regularUsers = users.stream()
        .filter(user -> user.getRole() == Role.USER)
        .toList();

    if (regularUsers.isEmpty() || sessions.isEmpty()) {
      log.warn("No regular users or sessions available for registration seeding");
      return;
    }

    // Keep track of sessions that already have registrations (OneToOne constraint)
    Set<Long> usedSessionIds = new HashSet<>();

    // Create registrations for about 50% of available sessions
    int maxRegistrations = Math.min(sessions.size() / 2, regularUsers.size());
    int createdRegistrations = 0;

    // Shuffle sessions to get random selection
    List<Session> shuffledSessions = sessions.stream()
        .collect(java.util.stream.Collectors.toList());
    java.util.Collections.shuffle(shuffledSessions);

    for (Session session : shuffledSessions) {
      if (createdRegistrations >= maxRegistrations) {
        break;
      }

      // Skip if this session already has a registration
      if (usedSessionIds.contains(session.getId())) {
        continue;
      }

      // Check if session already has a registration in database
      if (registrationRepository.existsBySessionId(session.getId())) {
        usedSessionIds.add(session.getId());
        continue;
      }

      // Select a random user for this session
      User randomUser = regularUsers.get(random.nextInt(regularUsers.size()));

      try {
        Registration registration = new Registration();

        // Fetch managed entities to avoid detached entity issues
        User managedUser = userRepository.findById(randomUser.getId()).orElse(randomUser);
        Session managedSession = sessionRepository.findById(session.getId()).orElse(session);

        registration.setUser(managedUser);
        registration.setSession(managedSession);
        registration.setPaid(random.nextBoolean()); // 50% chance of being paid
        registration.setCompleted(random.nextBoolean() && registration.isPaid()); // Only completed if paid
        registration.setTransmissionType(random.nextBoolean() ? TransmissionType.AUTOMATIC : TransmissionType.MANUAL);

        // If completed, add score and feedback
        if (registration.isCompleted()) {
          registration.setScore(random.nextInt(41) + 60); // Score between 60-100
          registration.setFeedback(generateRandomFeedback());
        }

        registrationRepository.save(registration);
        usedSessionIds.add(session.getId());
        createdRegistrations++;

        log.debug("Created registration for user {} and session {}",
            managedUser.getEmail(), managedSession.getId());

      } catch (Exception e) {
        log.warn("Failed to create registration for session {}: {}",
            session.getId(), e.getMessage());
        // Continue with next session instead of failing completely
      }
    }

    log.info("Created {} registrations out of {} possible sessions",
        createdRegistrations, sessions.size());
  }

  private String generateRandomFeedback() {
    String[] feedbacks = {
        "Excellent performance! Great control and awareness.",
        "Good session overall. Work on parking techniques.",
        "Very smooth driving. Continue practicing lane changes.",
        "Outstanding! Ready for advanced scenarios.",
        "Good progress. Focus on speed control.",
        "Well done! Excellent response to traffic situations.",
        "Great improvement shown. Keep up the good work!",
        "Solid performance. Practice more complex maneuvers."
    };
    return feedbacks[random.nextInt(feedbacks.length)];
  }

  // Method to manually trigger seeding (useful for testing)
  @Transactional
  public void forceSeedDatabase() {
    log.info("Force seeding database...");
    seedDatabase();
  }

  // Method to clear and reseed database (useful for development)
  @Transactional
  public void clearAndReseedDatabase() {
    log.info("Clearing and reseeding database...");

    // Clear all data in proper order (to handle foreign key constraints)
    registrationRepository.deleteAll();
    sessionRepository.deleteAll();
    scenarioRepository.deleteAll();
    userRepository.deleteAll();

    log.info("Database cleared. Starting fresh seed...");
    seedDatabase();
  }
}
