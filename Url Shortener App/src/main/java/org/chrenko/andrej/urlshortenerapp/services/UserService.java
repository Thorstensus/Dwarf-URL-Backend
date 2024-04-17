package org.chrenko.andrej.urlshortenerapp.services;

import org.chrenko.andrej.urlshortenerapp.entities.User;
import org.chrenko.andrej.urlshortenerapp.dto.registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.registration.RegistrationResponseDTO;
import org.chrenko.andrej.urlshortenerapp.dto.userstats.UserStatPageDTO;

import java.util.Optional;

public interface UserService {

  RegistrationResponseDTO registerUser(RegistrationRequestDTO request);

  Optional<User> extractUserFromToken(String token);

  String verifyMail(String token);

  UserStatPageDTO getUserStats(Integer page);
}
