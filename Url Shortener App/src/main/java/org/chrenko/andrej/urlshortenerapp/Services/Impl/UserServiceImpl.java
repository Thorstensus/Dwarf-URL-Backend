package org.chrenko.andrej.urlshortenerapp.Services.Impl;

import org.chrenko.andrej.urlshortenerapp.DTOs.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.RegistrationResponseDTO;
import org.chrenko.andrej.urlshortenerapp.Services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  @Override
  public RegistrationResponseDTO registerUser(RegistrationRequestDTO request) {
    return new RegistrationResponseDTO();
  }
}
