package org.chrenko.andrej.urlshortenerapp.Email;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.User;

import java.util.Map;

public interface EmailService {
  void send(String to, String subject, String template, Map<String, Object> variables);

  void sendVerificationMail(User user);
}
