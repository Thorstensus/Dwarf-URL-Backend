package org.chrenko.andrej.urlshortenerapp.Services.Impl;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.User;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationResponseDTO;
import org.chrenko.andrej.urlshortenerapp.Email.EmailService;
import org.chrenko.andrej.urlshortenerapp.Enum.Role;
import org.chrenko.andrej.urlshortenerapp.Exceptions.ExceptionService;
import org.chrenko.andrej.urlshortenerapp.Repositories.UserRepository;
import org.chrenko.andrej.urlshortenerapp.Security.Services.JwtService;
import org.chrenko.andrej.urlshortenerapp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final ExceptionService exceptionService;

  private final PasswordEncoder passwordEncoder;

  private final JwtService jwtService;

  private final EmailService emailService;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, ExceptionService exceptionService, PasswordEncoder passwordEncoder, JwtService jwtService, EmailService emailService) {
    this.userRepository = userRepository;
    this.exceptionService = exceptionService;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.emailService = emailService;
  }

  @Override
  public RegistrationResponseDTO registerUser(RegistrationRequestDTO request) {
    exceptionService.checkForRegisterErrors(request);
    User newUser = new User(request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
    userRepository.save(newUser);
    emailService.sendVerificationMail(newUser);
    return new RegistrationResponseDTO(newUser.getId(), newUser.getUsername(), newUser.getRole() == Role.ADMIN);
  }

  @Override
  public Optional<User> extractUserFromToken(String token) {
    return userRepository.findByEmail(jwtService.extractUsername(token));
  }

  @Override
  public String verifyMail(String token) {
    exceptionService.checkForVerifyEmailErrors(token);
    User user = userRepository.findByEmail(jwtService.extractUsername(token)).get();
    user.setVerified(true);
    userRepository.save(user);
    return "Account has been verified";
  }

}
