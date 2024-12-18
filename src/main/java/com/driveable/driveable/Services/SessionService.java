package com.driveable.driveable.Services;

import com.driveable.driveable.Models.Session;
import com.driveable.driveable.Repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class SessionService {
    public final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
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
    public void deleteSession(Long id) {
        Session session = findSessionById(id);
        sessionRepository.delete(session);
    }
}