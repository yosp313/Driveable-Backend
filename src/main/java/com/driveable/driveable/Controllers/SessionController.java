package com.driveable.driveable.Controllers;

import com.driveable.driveable.Models.Session;
import com.driveable.driveable.Models.User;
import com.driveable.driveable.Services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/sessions")
public class SessionController {
    public final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public List<Session> GetAllSessions(){
        return sessionService.findAll();
    }

    @GetMapping("/{id}")
    public Session GetSessionById(@PathVariable Long id){
        return sessionService.findSessionById(id);
    }

    @PutMapping("/register/{id}")
    public ResponseEntity.HeadersBuilder<?> RegisterSession(@PathVariable Long id){
        Session session = sessionService.findSessionById(id);

        if(session == null){
            return ResponseEntity.notFound();
        }

        if(session.getIsRegistered()){
            return ResponseEntity.badRequest();
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        session.setIsRegistered(true);
        session.setUser(user);

        Session updatedSession = sessionService.updateSession(session);

        return (ResponseEntity.HeadersBuilder<?>) ResponseEntity.ok(updatedSession);
    }

}
