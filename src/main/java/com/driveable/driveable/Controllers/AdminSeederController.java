package com.driveable.driveable.Controllers;

import com.driveable.driveable.Services.DatabaseSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/admin-dashboard/seeder")
@PreAuthorize("hasRole('ADMIN')")
public class AdminSeederController {

    private final DatabaseSeeder databaseSeeder;

    @Autowired
    public AdminSeederController(DatabaseSeeder databaseSeeder) {
        this.databaseSeeder = databaseSeeder;
    }

    @PostMapping("/seed")
    public ResponseEntity<?> seedDatabase() {
        try {
            databaseSeeder.forceSeedDatabase();
            return ResponseEntity.ok(Map.of(
                "message", "Database seeding completed successfully",
                "status", "success"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "message", "Error during database seeding: " + e.getMessage(),
                "status", "error"
            ));
        }
    }

    @PostMapping("/clear-and-seed")
    public ResponseEntity<?> clearAndSeedDatabase() {
        try {
            databaseSeeder.clearAndReseedDatabase();
            return ResponseEntity.ok(Map.of(
                "message", "Database cleared and reseeded successfully",
                "status", "success"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "message", "Error during database clearing and seeding: " + e.getMessage(),
                "status", "error"
            ));
        }
    }
}