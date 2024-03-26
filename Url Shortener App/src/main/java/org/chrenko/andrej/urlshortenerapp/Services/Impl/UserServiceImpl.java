package org.chrenko.andrej.urlshortenerapp.Services.Impl;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.User;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationResponseDTO;
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

  @Autowired
  public UserServiceImpl(UserRepository userRepository, ExceptionService exceptionService, PasswordEncoder passwordEncoder, JwtService jwtService) {
    this.userRepository = userRepository;
    this.exceptionService = exceptionService;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  @Override
  public RegistrationResponseDTO registerUser(RegistrationRequestDTO request) {
    exceptionService.checkForRegisterErrors(request);
    User newUser = new User(request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
    userRepository.save(newUser);
    //todo: logic to send verification email
    return new RegistrationResponseDTO(newUser.getId(), newUser.getUsername(), newUser.getRole() == Role.ADMIN);
  }

  @Override
  public Optional<User> findUserById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public Optional<User> extractUserFromToken(String token) {
    return userRepository.findByEmail(jwtService.extractUsername(token));
  }


}
