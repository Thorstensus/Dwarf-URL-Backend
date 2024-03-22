package org.chrenko.andrej.urlshortenerapp.Exceptions;

import org.chrenko.andrej.urlshortenerapp.DTOs.AuthenticationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Refresh_Access_Token.RefreshAccessRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationRequestDTO;

public interface ExceptionService {
  void checkForRegisterErrors(RegistrationRequestDTO requestDTO);

  void checkForAuthenticationErrors(AuthenticationRequestDTO requestDTO);

  void checkForRefreshTokenErrors(RefreshAccessRequestDTO requestDTO);

  boolean isValidEmailAddress(String email);

  boolean isSafePassword(String password);

  void throwNotValidEmailAddress();

  void throwEmailUsed();

  void throwUsernameUsed();

  void throwPasswordTooShort();

  void throwAllFieldsRequired();

  void throwPasswordNotSafe();

  void throwUserOrEmailNotMatch();

  void throwRefreshTokenNotExists();

  void throwRefreshTokenExpired(String id);

  void throwException(String message);
}
