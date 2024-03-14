package org.chrenko.andrej.urlshortenerapp.Security.Services;

import org.chrenko.andrej.urlshortenerapp.DTOs.AuthenticationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.AuthenticationResponseDTO;

public interface AuthenticationService {
  AuthenticationResponseDTO authenticateUser(AuthenticationRequestDTO requestDTO);
}
