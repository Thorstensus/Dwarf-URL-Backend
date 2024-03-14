package org.chrenko.andrej.urlshortenerapp.Security.Services.Impl;

import org.chrenko.andrej.urlshortenerapp.DTOs.AuthenticationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.AuthenticationResponseDTO;
import org.chrenko.andrej.urlshortenerapp.Exceptions.ExceptionService;
import org.chrenko.andrej.urlshortenerapp.Security.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private final ExceptionService exceptionService;

  @Autowired
  public AuthenticationServiceImpl(ExceptionService exceptionService) {
    this.exceptionService = exceptionService;
  }

  @Override
  public AuthenticationResponseDTO authenticateUser(AuthenticationRequestDTO requestDTO) {
    return new AuthenticationResponseDTO();
  }
}
