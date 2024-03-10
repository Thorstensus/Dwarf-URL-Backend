package org.chrenko.andrej.urlshortenerapp.Controllers;

import org.chrenko.andrej.urlshortenerapp.DTOs.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.chrenko.andrej.urlshortenerapp.DTOs.RegistrationResponseDTO;

@RestController
@RequestMapping("/api")
public class UnsecuredController {

  private final UserService userService;

  @Autowired
  public UnsecuredController (UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/users/register")
  public ResponseEntity<RegistrationResponseDTO> registerUser(@RequestBody RegistrationRequestDTO request) {
    return ResponseEntity.status(200).body(userService.registerUser(request));
  }
}
