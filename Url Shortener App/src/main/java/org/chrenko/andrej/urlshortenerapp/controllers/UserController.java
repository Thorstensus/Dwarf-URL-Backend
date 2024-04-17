package org.chrenko.andrej.urlshortenerapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.chrenko.andrej.urlshortenerapp.dto.authentication.AuthenticationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.authentication.AuthenticationResponseDTO;
import org.chrenko.andrej.urlshortenerapp.dto.refreshaccesstoken.RefreshAccessRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.refreshaccesstoken.RefreshAccessResponseDTO;
import org.chrenko.andrej.urlshortenerapp.dto.registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.security.services.AuthenticationService;
import org.chrenko.andrej.urlshortenerapp.security.services.RefreshTokenService;
import org.chrenko.andrej.urlshortenerapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.chrenko.andrej.urlshortenerapp.dto.registration.RegistrationResponseDTO;

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

  @Operation(summary = "Endpoint used for registering new users.")
  @PostMapping("/register")
  public ResponseEntity<RegistrationResponseDTO> registerUser(@RequestBody RegistrationRequestDTO requestDTO) {
    return ResponseEntity.status(200).body(userService.registerUser(requestDTO));
  }

  @Operation(summary = "Endpoint used for login which generates an access token and refresh token.")
  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponseDTO> authenticateUser(@RequestBody AuthenticationRequestDTO requestDTO) {
    return ResponseEntity.status(200).body(authenticationService.authenticateUser(requestDTO));
  }

  @Operation(summary = "Endpoint which generates a new access token if the sent refresh token is valid.")
  @PostMapping("/refresh-token")
  public ResponseEntity<RefreshAccessResponseDTO> refreshAccessToken(@RequestBody RefreshAccessRequestDTO requestDTO) {
    return ResponseEntity.status(200).body(refreshTokenService.generateNewToken(requestDTO));
  }

  @Operation(summary = "Endpoint which serves for email validation. A new verification token is generated after registration.")
  @GetMapping("/verification/{token}")
  public ResponseEntity<String> verifyEmail(@PathVariable String token) {
    return ResponseEntity.status(200).body(userService.verifyMail(token));
  }
}
