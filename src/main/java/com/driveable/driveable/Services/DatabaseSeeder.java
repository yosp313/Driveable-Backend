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

import java.util.List;
import java.util.Random;

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
    // Create mixed role users: 15 regular users, 2 admins, 3 instructors
    List<User> users = userFactory.createMixedRoleUsers(5, 1, 0);

    // Create some specific test users
    users.add(userFactory.createUserWithSpecificData("John", "Doe", "john.doe@test.com", Role.USER));
    users.add(userFactory.createUserWithSpecificData("Jane", "Smith", "jane.smith@test.com", Role.USER));
    users
        .add(userFactory.createUserWithSpecificData("Mike", "Johnson", "mike.johnson@instructor.com", Role.INSTRUCTOR));

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

  private void seedRegistrations(List<User> users, List<Session> sessions) {
    // Create some sample registrations (about 30% of users register for random
    // sessions)
    List<User> regularUsers = users.stream()
        .filter(user -> user.getRole() == Role.USER)
        .toList();

    int registrationsToCreate = Math.min(regularUsers.size() * 2, sessions.size());

    for (int i = 0; i < registrationsToCreate; i++) {
      User randomUser = regularUsers.get(random.nextInt(regularUsers.size()));
      Session randomSession = sessions.get(random.nextInt(sessions.size()));

      // Check if this user is already registered for this session
      boolean alreadyRegistered = registrationRepository.findByUserId(randomUser.getId())
          .stream()
          .anyMatch(reg -> reg.getSession().getId().equals(randomSession.getId()));

      if (!alreadyRegistered) {
        Registration registration = new Registration();
        registration.setUser(randomUser);
        registration.setSession(randomSession);
        registration.setPaid(random.nextBoolean()); // 50% chance of being paid
        registration.setCompleted(random.nextBoolean() && registration.isPaid()); // Only completed if paid
        registration.setTransmissionType(random.nextBoolean() ? TransmissionType.AUTOMATIC : TransmissionType.MANUAL);

        // If completed, add score and feedback
        if (registration.isCompleted()) {
          registration.setScore(random.nextInt(41) + 60); // Score between 60-100
          registration.setFeedback(generateRandomFeedback());
        }

        registrationRepository.save(registration);
      }
    }
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
  public void forceSeedDatabase() {
    log.info("Force seeding database...");
    seedDatabase();
  }

  // Method to clear and reseed database (useful for development)
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
