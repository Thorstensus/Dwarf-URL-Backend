package org.chrenko.andrej.urlshortenerapp.Services;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.User;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationResponseDTO;

import java.util.Optional;

public interface UserService {

  RegistrationResponseDTO registerUser(RegistrationRequestDTO request);

  Optional<User> findUserById(Long id);

  Optional<User> extractUserFromToken(String token);
}
