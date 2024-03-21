package org.chrenko.andrej.urlshortenerapp.Exceptions;

import org.chrenko.andrej.urlshortenerapp.DTOs.AuthenticationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationRequestDTO;

public interface ExceptionService {
  void checkForRegisterErrors(RegistrationRequestDTO requestDTO);

  void checkForAuthenticationErrors(AuthenticationRequestDTO requestDTO);

  boolean isValidEmailAddress(String email);

  boolean isSafePassword(String password);

  void throwNotValidEmailAddress();

  void throwEmailUsed();

  void throwUsernameUsed();

  void throwPasswordTooShort();

  void throwAllFieldsRequired();

  void throwPasswordNotSafe();

  void throwUserOrEmailNotMatch();

  void throwException(String message);
}
