package org.chrenko.andrej.urlshortenerapp.Exceptions;

import org.chrenko.andrej.urlshortenerapp.DTOs.Authentication.AuthenticationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Refresh_Access_Token.RefreshAccessRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.UrlShortener.ShortenedUrlRequestDTO;

public interface ExceptionService {
  void checkForRegisterErrors(RegistrationRequestDTO requestDTO);

  void checkForAuthenticationErrors(AuthenticationRequestDTO requestDTO);

  void checkForRefreshTokenErrors(RefreshAccessRequestDTO requestDTO);

  void checkForCreateShortenedUrlErrors(ShortenedUrlRequestDTO requestDTO, String jwtToken);

  boolean isValidEmailAddress(String email);

  boolean isSafePassword(String password);

  boolean isValidLink(String link);

  void throwNotValidEmailAddress();

  void throwEmailUsed();

  void throwUsernameUsed();

  void throwPasswordTooShort();

  void throwAllFieldsRequired();

  void throwPasswordNotSafe();

  void throwUserOrEmailNotMatch();

  void throwRefreshTokenNotExists();

  void throwRefreshTokenExpired(String id);

  void throwJwtTokenInvalid();

  void throwInvalidLink();

  void throwException(String message);
}
