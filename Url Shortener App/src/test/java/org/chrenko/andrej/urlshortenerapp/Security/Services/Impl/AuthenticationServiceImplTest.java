package org.chrenko.andrej.urlshortenerapp.Security.Services.Impl;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.RefreshToken;
import org.chrenko.andrej.urlshortenerapp.DB_Entities.User;
import org.chrenko.andrej.urlshortenerapp.DTOs.AuthenticationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.AuthenticationResponseDTO;
import org.chrenko.andrej.urlshortenerapp.Exceptions.ExceptionService;
import org.chrenko.andrej.urlshortenerapp.Repositories.UserRepository;
import org.chrenko.andrej.urlshortenerapp.Security.Services.JwtService;
import org.chrenko.andrej.urlshortenerapp.Security.Services.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

  @Mock ExceptionService exceptionService;

  @Mock UserRepository userRepository;

  @Mock AuthenticationManager authenticationManager;

  @Mock JwtService jwtService;

  @Mock RefreshTokenService refreshTokenService;

  @InjectMocks AuthenticationServiceImpl authenticationService;

  @Test
  void authenticateUserGoesThrough() {
    AuthenticationRequestDTO sampleRequestDTO = new AuthenticationRequestDTO("truemail@sample.com", "truepassword");
    
    User sampleUser = new User("JohnDoe", "truemail@sample.com", "truepassword");
    sampleUser.setId(1L);
    RefreshToken sampleRefreshToken = new RefreshToken(new Date(System.currentTimeMillis() + 10000), sampleUser);
    sampleRefreshToken.setId(UUID.fromString("bbcc4621-d88f-4a94-ae2f-b38072bf5087"));

    doNothing().when(exceptionService).checkForAuthenticationErrors(sampleRequestDTO);
    when(userRepository.findByEmail(sampleRequestDTO.getEmail())).thenReturn(Optional.of(sampleUser));
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
    when(jwtService.generateToken(any(Map.class), any(User.class))).thenReturn("sampleAccessToken");
    when(refreshTokenService.createRefreshToken(sampleRequestDTO.getEmail())).thenReturn(sampleRefreshToken);

    AuthenticationResponseDTO expectedResponse = new AuthenticationResponseDTO("ok", sampleRefreshToken.getId(), "sampleAccessToken");
    AuthenticationResponseDTO actualResponse = authenticationService.authenticateUser(sampleRequestDTO);

    assertEquals(expectedResponse.getStatus(),actualResponse.getStatus());
    assertEquals(expectedResponse.getAccessToken(),actualResponse.getAccessToken());
    assertEquals(expectedResponse.getRefreshToken(),actualResponse.getRefreshToken());
  }
}
