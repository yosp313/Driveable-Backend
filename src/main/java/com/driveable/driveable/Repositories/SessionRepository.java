package com.driveable.driveable.Repositories;

import com.driveable.driveable.Models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
  Optional<Session> findById(long Id);

  // This will automatically create the appropriate query
  @Query("SELECT s FROM Session s WHERE LOWER(s.location) LIKE LOWER(CONCAT('%', :searchText, '%'))")
  Optional<List<Session>> searchForASession(@Param("searchText") String searchText);

  @Query("SELECT s FROM Session s WHERE s.isAvailable = true")
  Optional<List<Session>> findAvailableSessions();
}
