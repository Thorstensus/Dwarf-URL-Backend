package org.chrenko.andrej.urlshortenerapp.email;

import org.chrenko.andrej.urlshortenerapp.entities.User;

import java.util.Map;

public interface EmailService {
  void send(String to, String subject, String template, Map<String, Object> variables);

  void sendVerificationMail(User user);
}
