package org.chrenko.andrej.urlshortenerapp.Security.Services;

import org.chrenko.andrej.urlshortenerapp.DTOs.Authentication.AuthenticationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Authentication.AuthenticationResponseDTO;

public interface AuthenticationService {
  AuthenticationResponseDTO authenticateUser(AuthenticationRequestDTO requestDTO);
}
