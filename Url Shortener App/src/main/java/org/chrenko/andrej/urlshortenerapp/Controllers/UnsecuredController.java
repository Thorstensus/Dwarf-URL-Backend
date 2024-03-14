package org.chrenko.andrej.urlshortenerapp.Controllers;

import org.chrenko.andrej.urlshortenerapp.DTOs.AuthenticationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.AuthenticationResponseDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.Security.Services.AuthenticationService;
import org.chrenko.andrej.urlshortenerapp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationResponseDTO;

@RestController
@RequestMapping("/api")
public class UnsecuredController {

  private final UserService userService;

  private final AuthenticationService authenticationService;

  @Autowired
  public UnsecuredController (UserService userService, AuthenticationService authenticationService) {
    this.userService = userService;
    this.authenticationService = authenticationService;
  }

  @PostMapping("/users/register")
  public ResponseEntity<RegistrationResponseDTO> registerUser(@RequestBody RegistrationRequestDTO requestDTO) {
    return ResponseEntity.status(200).body(userService.registerUser(requestDTO));
  }

  @PostMapping("/users/login")
  public ResponseEntity<AuthenticationResponseDTO> authenticateUser(@RequestBody AuthenticationRequestDTO requestDTO) {
    return ResponseEntity.status(200).body(authenticationService.authenticateUser(requestDTO));
  }
}
