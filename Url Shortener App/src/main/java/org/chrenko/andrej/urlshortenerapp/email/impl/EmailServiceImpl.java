package org.chrenko.andrej.urlshortenerapp.email.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.chrenko.andrej.urlshortenerapp.entities.User;
import org.chrenko.andrej.urlshortenerapp.email.EmailService;
import org.chrenko.andrej.urlshortenerapp.email.ThymeLeafService;
import org.chrenko.andrej.urlshortenerapp.security.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

  @Value("${MAIL_USERNAME}")
  private String MAIL_USERNAME;

  private final JavaMailSender mailSender;
  private final JwtService jwtService;

  private final ThymeLeafService thymeLeafService;

  @Autowired
  public EmailServiceImpl(JavaMailSender mailSender, JwtService jwtService, ThymeLeafService thymeLeafService) {
    this.mailSender = mailSender;
    this.jwtService = jwtService;
    this.thymeLeafService = thymeLeafService;
  }

  @Override
  public void send(String to, String subject, String template, Map<String, Object> variables) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper =
          new MimeMessageHelper(
              mimeMessage,
              MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
              StandardCharsets.UTF_8.name());
      helper.setFrom(MAIL_USERNAME);
      helper.setText(thymeLeafService.createContent(template, variables), true);
      helper.setTo(to);
      helper.setSubject(subject);

      mailSender.send(mimeMessage);

    } catch (MessagingException e) {
      System.out.println("Failed to send email");
      throw new IllegalStateException("Failed to send email");
    }
  }

  @Override
  public void sendVerificationMail(User user) {
    Map<String, Object> variables = new HashMap<>();
    variables.put(
        "link",
        "http://localhost:8080/api/users/verification/" + jwtService.generateVerifyToken(user));
    variables.put("name", user.getName());

    send(user.getEmail(), "Please confirm your e-mail", "verification-email", variables);
  }
}
