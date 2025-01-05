package com.driveable.driveable.Controllers;

import com.driveable.driveable.Models.Session;
import com.driveable.driveable.Models.User;
import com.driveable.driveable.Services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/adminsessions")
public class AdminSessionController {
    public final SessionService sessionService;
    @Autowired
    public AdminSessionController(SessionService sessionService) {this.sessionService = sessionService;}

    @GetMapping
    public List<Session> GetAllSessions(){
        return sessionService.findAll();
    }

    @GetMapping("/{id}")
    public Session GetSessionById(@PathVariable Long id){
        return sessionService.findSessionById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteSession(@PathVariable Long id){
        sessionService.deleteSession(id);

        return ResponseEntity.noContent().build();
    }
    @PostMapping
    public ResponseEntity<Session> createSession(@RequestBody Session session) {
        Session createdSession = sessionService.createSession(session);
        return ResponseEntity.ok(createdSession);
    }
}
