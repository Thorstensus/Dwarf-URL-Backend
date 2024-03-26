package org.chrenko.andrej.urlshortenerapp.Controllers;

import org.chrenko.andrej.urlshortenerapp.DTOs.Authentication.AuthenticationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Authentication.AuthenticationResponseDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Refresh_Access_Token.RefreshAccessRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Refresh_Access_Token.RefreshAccessResponseDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.Security.Services.AuthenticationService;
import org.chrenko.andrej.urlshortenerapp.Security.Services.RefreshTokenService;
import org.chrenko.andrej.urlshortenerapp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationResponseDTO;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  private final AuthenticationService authenticationService;

  private final RefreshTokenService refreshTokenService;

  @Autowired
  public UserController(UserService userService, AuthenticationService authenticationService, RefreshTokenService refreshTokenService) {
    this.userService = userService;
    this.authenticationService = authenticationService;
    this.refreshTokenService = refreshTokenService;
  }

  @PostMapping("/register")
  public ResponseEntity<RegistrationResponseDTO> registerUser(@RequestBody RegistrationRequestDTO requestDTO) {
    return ResponseEntity.status(200).body(userService.registerUser(requestDTO));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponseDTO> authenticateUser(@RequestBody AuthenticationRequestDTO requestDTO) {
    return ResponseEntity.status(200).body(authenticationService.authenticateUser(requestDTO));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<RefreshAccessResponseDTO> refreshAccessToken(@RequestBody RefreshAccessRequestDTO requestDTO) {
    return ResponseEntity.status(200).body(refreshTokenService.generateNewToken(requestDTO));
  }
}
