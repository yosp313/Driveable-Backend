package com.driveable.driveable.Controllers;

import com.driveable.driveable.Models.Session;
import com.driveable.driveable.Services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
