package com.driveable.driveable.Factories;

import com.driveable.driveable.Models.Scenario;
import com.driveable.driveable.Models.Session;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class SessionFactory {

  private final Random random = new Random();

  private final String[] locations = {
      "Downtown Training Center", "Westside VR Hub", "North Campus Facility",
      "East Side Learning Center", "Central VR Academy", "Southport Training Hub",
      "Metro VR Center", "Riverside Training Facility", "Hillside VR Campus",
      "Valley Training Center", "Uptown VR Studio", "Midtown Learning Hub"
  };

  public Session createSession(Scenario scenario) {
    String location = locations[random.nextInt(locations.length)];
    Date sessionDate = generateRandomFutureDate();
    int maxParticipants = 5; // Default max participants

    return new Session(scenario, sessionDate, maxParticipants, location);
  }

  public Session createSessionWithSpecificData(Scenario scenario, Date date, String location) {
    int maxParticipants = 5; // Default max participants
    return new Session(scenario, date, maxParticipants, location);
  }

  public Session createSessionWithDateTime(Scenario scenario, LocalDateTime dateTime) {
    String location = locations[random.nextInt(locations.length)];
    Date sessionDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    int maxParticipants = 5; // Default max participants

    return new Session(scenario, sessionDate, maxParticipants, location);
  }

  public Session createSessionWithMaxParticipants(Scenario scenario, int maxParticipants) {
    String location = locations[random.nextInt(locations.length)];
    Date sessionDate = generateRandomFutureDate();

    return new Session(scenario, sessionDate, maxParticipants, location);
  }

  public Session createSessionWithAllData(Scenario scenario, Date date, String location, int maxParticipants) {
    return new Session(scenario, date, maxParticipants, location);
  }

  public List<Session> createSessions(List<Scenario> scenarios, int sessionsPerScenario) {
    List<Session> sessions = new ArrayList<>();

    for (Scenario scenario : scenarios) {
      for (int i = 0; i < sessionsPerScenario; i++) {
        sessions.add(createSession(scenario));
      }
    }

    return sessions;
  }

  public List<Session> createSessionsForNextDays(List<Scenario> scenarios, int days) {
    List<Session> sessions = new ArrayList<>();
    LocalDateTime now = LocalDateTime.now();

    for (int day = 1; day <= days; day++) {
      LocalDateTime sessionDay = now.plusDays(day);

      // Create 3-5 sessions per day at different times
      int sessionsPerDay = random.nextInt(3) + 3; // 3 to 5 sessions

      for (int session = 0; session < sessionsPerDay; session++) {
        // Random hour between 9 AM and 6 PM
        int hour = random.nextInt(10) + 9;
        int minute = random.nextInt(4) * 15; // 0, 15, 30, or 45 minutes

        LocalDateTime sessionDateTime = sessionDay.withHour(hour).withMinute(minute).withSecond(0);
        Scenario randomScenario = scenarios.get(random.nextInt(scenarios.size()));

        sessions.add(createSessionWithDateTime(randomScenario, sessionDateTime));
      }
    }

    return sessions;
  }

  public List<Session> createWeeklySchedule(List<Scenario> scenarios) {
    List<Session> sessions = new ArrayList<>();
    LocalDateTime startOfWeek = LocalDateTime.now().plusDays(1);

    // Monday to Friday schedule
    for (int day = 0; day < 5; day++) {
      LocalDateTime currentDay = startOfWeek.plusDays(day);

      // Morning sessions (9 AM, 10 AM, 11 AM)
      for (int hour = 9; hour <= 11; hour++) {
        Scenario scenario = scenarios.get(random.nextInt(scenarios.size()));
        LocalDateTime sessionTime = currentDay.withHour(hour).withMinute(0).withSecond(0);
        sessions.add(createSessionWithDateTime(scenario, sessionTime));
      }

      // Afternoon sessions (2 PM, 3 PM, 4 PM, 5 PM)
      for (int hour = 14; hour <= 17; hour++) {
        Scenario scenario = scenarios.get(random.nextInt(scenarios.size()));
        LocalDateTime sessionTime = currentDay.withHour(hour).withMinute(0).withSecond(0);
        sessions.add(createSessionWithDateTime(scenario, sessionTime));
      }
    }

    return sessions;
  }

  private Date generateRandomFutureDate() {
    LocalDateTime now = LocalDateTime.now();

    // Generate a date between 1 to 30 days in the future
    int daysToAdd = random.nextInt(30) + 1;
    int hour = random.nextInt(10) + 9; // 9 AM to 6 PM
    int minute = random.nextInt(4) * 15; // 0, 15, 30, or 45 minutes

    LocalDateTime futureDateTime = now.plusDays(daysToAdd)
        .withHour(hour)
        .withMinute(minute)
        .withSecond(0);

    return Date.from(futureDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }
}
