package org.chrenko.andrej.urlshortenerapp.Services.Impl;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.User;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationResponseDTO;
import org.chrenko.andrej.urlshortenerapp.Exceptions.ExceptionService;
import org.chrenko.andrej.urlshortenerapp.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock private ExceptionService exceptionService;

  @Mock private UserRepository userRepository;

  @Mock private PasswordEncoder passwordEncoder;

  @InjectMocks private UserServiceImpl userService;

  @Test
  void registerUserBasicTest() {
    RegistrationRequestDTO requestDTO = new RegistrationRequestDTO("Johnny",
        "john@gmail.com",
        "secretPass1@");

    doNothing().when(exceptionService).checkForRegisterErrors(any(RegistrationRequestDTO.class));
    doReturn(null).when(userRepository).save(any(User.class));
    when(passwordEncoder.encode(any(String.class))).thenReturn("hashedPassword");

    RegistrationResponseDTO expectedResponseDTO = new RegistrationResponseDTO(null, "john@gmail.com", false);
    RegistrationResponseDTO actualResponseDTO = userService.registerUser(requestDTO);

    assertEquals(expectedResponseDTO,actualResponseDTO);
  }
}