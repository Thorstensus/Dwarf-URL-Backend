package org.chrenko.andrej.urlshortenerapp.exceptions.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.chrenko.andrej.urlshortenerapp.entities.User;
import org.chrenko.andrej.urlshortenerapp.dto.authentication.AuthenticationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.refreshaccesstoken.RefreshAccessRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.urlshortener.DeleteShortenedUrlRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.urlshortener.ShortenedUrlRequestDTO;
import org.chrenko.andrej.urlshortenerapp.exceptions.dto.ApiRequestException;
import org.chrenko.andrej.urlshortenerapp.repositories.RefreshTokenRepository;
import org.chrenko.andrej.urlshortenerapp.repositories.ShortenedURLRepository;
import org.chrenko.andrej.urlshortenerapp.repositories.UserRepository;
import org.chrenko.andrej.urlshortenerapp.security.services.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExceptionServiceImplTest {

  @Mock UserRepository userRepository;

  @Mock HttpServletRequest httpServletRequest;

  @Mock PasswordEncoder passwordEncoder;

  @Mock JwtService jwtService;

  @Mock RefreshTokenRepository refreshTokenRepository;

  @Mock
  ShortenedURLRepository shortenedURLRepository;

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
    when(userRepository.existsByName(mockRequest.getName())).thenReturn(true);

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
  void checkForAuthenticationErrorsDetectsUsernamePasswordMismatch_Email_not_registered() {
    AuthenticationRequestDTO sampleLogin = new AuthenticationRequestDTO(
        "falsemail@sample.com",
        "truepassword");

    ApiRequestException expectedException = new ApiRequestException(
        "/api/test",
        "The e-mail and password don't match");

    when(userRepository.findByEmail(sampleLogin.getEmail())).thenReturn(Optional.empty());

    ApiRequestException actualException = assertThrows(
        ApiRequestException.class, () -> {
          exceptionService.checkForAuthenticationErrors(sampleLogin);
        }
    );
    assertEquals(expectedException.getMessage(), actualException.getMessage());
  }

  @Test
  void checkForAuthenticationErrorsDetectsUsernamePasswordMismatch_Wrong_Password() {
    AuthenticationRequestDTO sampleLogin = new AuthenticationRequestDTO(
        "truemail@sample.com",
        "wrongpassword");

    ApiRequestException expectedException = new ApiRequestException(
        "/api/test",
        "The e-mail and password don't match");

    User sampleUser = new User("John Doe", "truemail@sample.com", "truepassword");

    when(userRepository.findByEmail(sampleLogin.getEmail())).thenReturn(Optional.of(sampleUser));

    when(passwordEncoder.matches(sampleLogin.getPassword(), sampleUser.getPassword())).thenReturn(false);

    ApiRequestException actualException = assertThrows(
        ApiRequestException.class, () -> {
          exceptionService.checkForAuthenticationErrors(sampleLogin);
        }
    );
    assertEquals(expectedException.getMessage(), actualException.getMessage());
  }

  @Test
  void checkForRefreshTokenErrorsThrowsNotExists() {
    UUID uuid = UUID.randomUUID();
    RefreshAccessRequestDTO sampleRequest = new RefreshAccessRequestDTO(uuid);
    ApiRequestException expected = new ApiRequestException("/api/test",
        "Refresh Token does not exist");

    when(refreshTokenRepository.findRefreshTokenById(uuid)).thenReturn(Optional.empty());

    ApiRequestException actual = assertThrows(
        ApiRequestException.class, () -> {
          exceptionService.checkForRefreshTokenErrors(sampleRequest);
        }
    );
    assertEquals(expected.getMessage(), actual.getMessage());
  }

  @Test
  void checkForCreateShortenedUrlErrorsThrowsJwtInvalid() {
    ShortenedUrlRequestDTO sampleRequest = new ShortenedUrlRequestDTO("https://randomLink.com");
    String jwt = "sampleJwt";
    ApiRequestException expected = new ApiRequestException("/api/test",
        "Unknown Error: Invalid Token");

    when(userRepository.findByEmail("sampleMail@sample.com")).thenReturn(Optional.empty());
    when(jwtService.extractUsername(jwt)).thenReturn("sampleMail@sample.com");

    ApiRequestException actual = assertThrows(ApiRequestException.class, () -> {
      exceptionService.checkForCreateShortenedUrlErrors(sampleRequest, jwt);
    });
    assertEquals(expected.getMessage(), actual.getMessage());
  }

  @Test
  void checkForCreateShortenedUrlErrorsThrowsLinkInvalid() {
    ShortenedUrlRequestDTO sampleRequest = new ShortenedUrlRequestDTO("http:randomLink.no");
    String jwt = "sampleJwt";
    User sampleUser = new User();
    ApiRequestException expected = new ApiRequestException("/api/test",
        "Please input a valid link");

    when(userRepository.findByEmail("sampleMail@sample.com")).thenReturn(Optional.of(sampleUser));
    when(jwtService.extractUsername(jwt)).thenReturn("sampleMail@sample.com");

    ApiRequestException actual = assertThrows(ApiRequestException.class, () -> {
      exceptionService.checkForCreateShortenedUrlErrors(sampleRequest, jwt);
    });
    assertEquals(expected.getMessage(), actual.getMessage());
  }

  @Test
  void checkForDeleteShortenedUrlErrorsThrowsJwtInvalid() {
    DeleteShortenedUrlRequestDTO sampleRequest = new DeleteShortenedUrlRequestDTO("1");
    String jwt = "sampleJwt";
    ApiRequestException expected = new ApiRequestException("/api/test",
        "Unknown Error: Invalid Token");

    when(userRepository.findByEmail("sampleMail@sample.com")).thenReturn(Optional.empty());
    when(jwtService.extractUsername(jwt)).thenReturn("sampleMail@sample.com");

    ApiRequestException actual = assertThrows(ApiRequestException.class,
        () -> {
          exceptionService.checkForDeleteShortenedUrlErrors(sampleRequest, jwt);
        });
    assertEquals(expected.getMessage(), actual.getMessage());
  }

  @Test
  void checkForDeleteShortenedUrlErrorsThrowsLinkInaccessible() {
    UUID randomUuid = UUID.randomUUID();
    DeleteShortenedUrlRequestDTO sampleRequest = new DeleteShortenedUrlRequestDTO(randomUuid.toString());
    String jwt = "sampleJwt";
    User sampleUser = new User();
    ApiRequestException expected = new ApiRequestException("/api/test",
        "Only links created by you can be deleted");

    when(userRepository.findByEmail("sampleMail@sample.com")).thenReturn(Optional.of(sampleUser));
    when(jwtService.extractUsername(jwt)).thenReturn("sampleMail@sample.com");
    when(shortenedURLRepository.findById(randomUuid)).thenReturn(Optional.empty());

    ApiRequestException actual = assertThrows(ApiRequestException.class,
        () -> {
          exceptionService.checkForDeleteShortenedUrlErrors(sampleRequest, jwt);
        });
    assertEquals(expected.getMessage(), actual.getMessage());
  }

  @Test
  void checkForVerifyEmailErrorsThrowsUserNotFound() {
    String token = "randomToken";
    ApiRequestException expected = new ApiRequestException("/api/test",
        "User not found");

    when(userRepository.findByEmail("randomMail")).thenReturn(Optional.empty());
    when(jwtService.extractUsername(token)).thenReturn("randomMail");

    ApiRequestException actual = assertThrows(ApiRequestException.class,
        () -> {
          exceptionService.checkForVerifyEmailErrors(token);
        });
    assertEquals(expected.getMessage(), actual.getMessage());
  }

  @Test
  void checkForVerifyEmailErrorsThrowsUserAlreadyVerified() {
    String token = "randomToken";
    User randomUser = new User();
    randomUser.setVerified(true);
    ApiRequestException expected = new ApiRequestException("/api/test",
        "User has already been verified");

    when(userRepository.findByEmail("randomMail")).thenReturn(Optional.of(randomUser));
    when(jwtService.extractUsername(token)).thenReturn("randomMail");

    ApiRequestException actual = assertThrows(ApiRequestException.class,
        () -> {
          exceptionService.checkForVerifyEmailErrors(token);
        });
    assertEquals(expected.getMessage(), actual.getMessage());
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

  @Test
  void isValidLinkDetectsInvalidLink() {
    String invalidLink = "https:invalid.kek";
    assertFalse(exceptionService.isValidLink(invalidLink));
  }

  @Test
  void isValidLinkApprovesValidLink() {
    String validLink = "https://validlink.com";
    assertTrue(exceptionService.isValidLink(validLink));
  }

}