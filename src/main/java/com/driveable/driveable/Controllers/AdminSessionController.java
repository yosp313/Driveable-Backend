package com.driveable.driveable.Controllers;

import com.driveable.driveable.Models.Session;
import com.driveable.driveable.Models.User;
import com.driveable.driveable.Services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin-dashboard/sessions")
public class AdminSessionController {
    public final SessionService sessionService;
    @Autowired
    public AdminSessionController(SessionService sessionService) {this.sessionService = sessionService;}

    @GetMapping
    public ResponseEntity<List<Session>> GetAllSessions(){
        List<Session> sessions = sessionService.findAll();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Session> GetSessionById(@PathVariable Long id){
        Session session = sessionService.findSessionById(id);
        return ResponseEntity.ok(session);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSession(@PathVariable Long id){
        sessionService.deleteSession(id);

        return ResponseEntity.noContent().build();
    }
    @PostMapping
    public ResponseEntity<Session> createSession(@RequestBody Session session) {
        Session createdSession = sessionService.createSession(session);
        return ResponseEntity.ok(createdSession);
    }
}
