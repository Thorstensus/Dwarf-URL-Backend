package org.chrenko.andrej.urlshortenerapp.security.services;

import org.chrenko.andrej.urlshortenerapp.dto.authentication.AuthenticationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.authentication.AuthenticationResponseDTO;

public interface AuthenticationService {
  AuthenticationResponseDTO authenticateUser(AuthenticationRequestDTO requestDTO);
}
