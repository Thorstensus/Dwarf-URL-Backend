package org.chrenko.andrej.urlshortenerapp.Services;

import org.chrenko.andrej.urlshortenerapp.DTOs.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.RegistrationResponseDTO;

public interface UserService {
  
  RegistrationResponseDTO registerUser(RegistrationRequestDTO request);
}
