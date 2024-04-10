package org.chrenko.andrej.urlshortenerapp.Services;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.User;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationResponseDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.UserStatPageDTO;

import java.util.Optional;

public interface UserService {

  RegistrationResponseDTO registerUser(RegistrationRequestDTO request);

  Optional<User> extractUserFromToken(String token);

  String verifyMail(String token);

  UserStatPageDTO getUserStats(Integer page);
}
