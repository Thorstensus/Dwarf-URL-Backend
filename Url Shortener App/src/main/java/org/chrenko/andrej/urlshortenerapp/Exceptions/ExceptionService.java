package org.chrenko.andrej.urlshortenerapp.Exceptions;

import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationRequestDTO;

public interface ExceptionService {
  void checkForRegisterErrors(RegistrationRequestDTO requestDTO);

  boolean isValidEmailAddress(String email);

  boolean isSafePassword(String password);

  void throwNotValidEmailAddress();

  void throwEmailUsed();

  void throwUsernameUsed();

  void throwPasswordTooShort();

  void throwAllFieldsRequired();

  void throwPasswordNotSafe();

  void throwException(String message);
}
