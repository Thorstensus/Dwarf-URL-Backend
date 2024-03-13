package org.chrenko.andrej.urlshortenerapp.Exceptions.Impl;

import jakarta.servlet.http.HttpServletRequest;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.Exceptions.DTOs.ApiRequestException;
import org.chrenko.andrej.urlshortenerapp.Exceptions.ExceptionService;
import org.chrenko.andrej.urlshortenerapp.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExceptionServiceImplTest {

  @Mock UserRepository userRepository;

  @Mock HttpServletRequest httpServletRequest;
  @InjectMocks ExceptionServiceImpl exceptionService;

  @Test
  void checkForRegisterErrorsThrowsAllFieldsRequired() {
    RegistrationRequestDTO wrongRequest = null;

    ApiRequestException expectedException = new ApiRequestException("/api/test", "All fields must be filled");

    when(httpServletRequest.getRequestURI()).thenReturn("/api/test");

    ApiRequestException actualException = assertThrows(
        ApiRequestException.class, () -> {
          exceptionService.checkForRegisterErrors(wrongRequest);
        }
    );
    assertEquals(expectedException.getMessage(), actualException.getMessage());
  }

  @Test
  void checkForRegisterErrorsThrowsEmailUsed() {
    RegistrationRequestDTO mockRequest = new RegistrationRequestDTO("Johnny", "usedmail@gmail.com", "randomPass123?");

    ApiRequestException expectedException = new ApiRequestException("/api/test", "The entered e-mail is already in use");

    when(httpServletRequest.getRequestURI()).thenReturn("/api/test");
    when(userRepository.existsByEmail(mockRequest.getEmail())).thenReturn(true);

    ApiRequestException actualException = assertThrows(
        ApiRequestException.class, () -> {
          exceptionService.checkForRegisterErrors(mockRequest);
        }
    );
    assertEquals(expectedException.getMessage(), actualException.getMessage());
  }

  @Test
  void checkForRegisterErrorsThrowsUsernameUsed() {
    RegistrationRequestDTO mockRequest = new RegistrationRequestDTO("usedJohnny", "randommail@gmail.com", "randomPass123?");

    ApiRequestException expectedException = new ApiRequestException("/api/test", "The entered username is already in use");

    when(httpServletRequest.getRequestURI()).thenReturn("/api/test");
    when(userRepository.existsByUsername(mockRequest.getUsername())).thenReturn(true);

    ApiRequestException actualException = assertThrows(
        ApiRequestException.class, () -> {
          exceptionService.checkForRegisterErrors(mockRequest);
        }
    );
    assertEquals(expectedException.getMessage(), actualException.getMessage());
  }

  @Test
  void checkForRegisterErrorsThrowsPasswordTooShort() {
    RegistrationRequestDTO mockRequest = new RegistrationRequestDTO("usedJohnny", "randommail@gmail.com", "1?Short");

    ApiRequestException expectedException = new ApiRequestException("/api/test", "The password must contain at least 8 characters");

    when(httpServletRequest.getRequestURI()).thenReturn("/api/test");

    ApiRequestException actualException = assertThrows(
        ApiRequestException.class, () -> {
          exceptionService.checkForRegisterErrors(mockRequest);
        }
    );
    assertEquals(expectedException.getMessage(), actualException.getMessage());
  }

  @Test
  void isValidEmailAddressDetectsInvalidEmail() {
    String wrongEmail = "wrongemail.com";
    assertFalse(exceptionService.isValidEmailAddress(wrongEmail));
  }

  @Test
  void isValidEmailAddressWorksWithCorrectEmail() {
    String wrongEmail = "correctemail@gmail.com";
    assertTrue(exceptionService.isValidEmailAddress(wrongEmail));
  }

  @Test
  void isSafePasswordDetectsUnsafePassword() {
    String weakPassword = "weak234";
    assertFalse(exceptionService.isSafePassword(weakPassword));
  }

  @Test
  void isSafePasswordApprovesSafePassword() {
    String strongPassword = "Strong234!";
    assertTrue(exceptionService.isSafePassword(strongPassword));
  }
}