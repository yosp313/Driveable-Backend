package com.driveable.driveable.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
  UserService userService;
  // JavaMailSender javaMailSender;
  private String sender = "drivable@email.com";

  @Autowired
  public MailService(UserService userService, JavaMailSender javaMailSender) {
    this.userService = userService;
    // this.javaMailSender = javaMailSender;
  }

  // public void sendEmail(String to, String subject, String body, Long id) {
  // String userEmail = userService.findUserById(id).getEmail();
  //
  // if (userEmail == null || userEmail.isEmpty()) {
  // System.out.println("User email not found for ID: " + id);
  // return;
  // }
  //
  // try {
  // SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
  //
  // simpleMailMessage.setTo(to);
  // simpleMailMessage.setFrom(sender);
  // simpleMailMessage.setSubject(subject);
  // simpleMailMessage.setText(body);
  //
  // javaMailSender.send(simpleMailMessage);
  // } catch (Exception e) {
  // e.printStackTrace();
  // System.out.println("Error sending email to " + userEmail);
  // return;
  // }
  // }
}
