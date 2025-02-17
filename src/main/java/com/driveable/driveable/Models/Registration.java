package com.driveable.driveable.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "registrations")
public class Registration {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, unique = true)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false) // Foreign Key to Session
  private User user;

  @ManyToOne
  @JoinColumn(name = "session_id", nullable = false) // Foreign Key to Session
  private Session session;

  @Column(nullable = false)
  private boolean isPaid;

  @Column(nullable = false)
  private int score;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }

  public boolean isPaid() {
    return isPaid;
  }

  public void setPaid(boolean isPaid) {
    this.isPaid = isPaid;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

}
