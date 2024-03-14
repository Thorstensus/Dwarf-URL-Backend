package org.chrenko.andrej.urlshortenerapp.Services;

import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationResponseDTO;

public interface UserService {

  RegistrationResponseDTO registerUser(RegistrationRequestDTO request);

}
