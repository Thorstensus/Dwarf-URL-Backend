package org.chrenko.andrej.urlshortenerapp.services.impl;

import org.chrenko.andrej.urlshortenerapp.entities.ShortenedURL;
import org.chrenko.andrej.urlshortenerapp.entities.User;
import org.chrenko.andrej.urlshortenerapp.dto.registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.registration.RegistrationResponseDTO;
import org.chrenko.andrej.urlshortenerapp.dto.userstats.UserStatPageDTO;
import org.chrenko.andrej.urlshortenerapp.dto.userstats.UserStatDTO;
import org.chrenko.andrej.urlshortenerapp.email.EmailService;
import org.chrenko.andrej.urlshortenerapp.enums.Role;
import org.chrenko.andrej.urlshortenerapp.exceptions.ExceptionService;
import org.chrenko.andrej.urlshortenerapp.repositories.UserRepository;
import org.chrenko.andrej.urlshortenerapp.security.services.JwtService;
import org.chrenko.andrej.urlshortenerapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

  @Override
  public UserStatPageDTO getUserStats(Integer page) {
    exceptionService.checkForUserStatsErrors(page);
    int realPageIndex = page - 1;
    List<UserStatDTO> userStats = new ArrayList<>();
    Pageable pageable = PageRequest.of(realPageIndex, 10);
    Page<User> users = userRepository.findAllByTotalClicksDesc(pageable);
    Long userRank = realPageIndex * 10L + 1;
    for (User user : users) {
      List<ShortenedURL> currentUserUrls = user.getUrls();
      Long clickCount = 0L;
      for (ShortenedURL url : currentUserUrls) {
        clickCount += url.getClicks().size();
      }
      UserStatDTO currentUserStat = new UserStatDTO(userRank, user.getId(), user.getUsername(), clickCount);
      userStats.add(currentUserStat);
      userRank++;
    }
    return new UserStatPageDTO(userStats);
  }

}
