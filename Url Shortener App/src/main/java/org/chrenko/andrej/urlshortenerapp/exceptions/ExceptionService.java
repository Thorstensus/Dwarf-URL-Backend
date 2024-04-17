package org.chrenko.andrej.urlshortenerapp.exceptions;

import org.chrenko.andrej.urlshortenerapp.dto.authentication.AuthenticationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.refreshaccesstoken.RefreshAccessRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.urlshortener.DeleteShortenedUrlRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.urlshortener.ShortenedUrlRequestDTO;

public interface ExceptionService {
  void checkForRegisterErrors(RegistrationRequestDTO requestDTO);

  void checkForAuthenticationErrors(AuthenticationRequestDTO requestDTO);

  void checkForRefreshTokenErrors(RefreshAccessRequestDTO requestDTO);

  void checkForCreateShortenedUrlErrors(ShortenedUrlRequestDTO requestDTO, String jwtToken);

  void checkForDeleteShortenedUrlErrors(DeleteShortenedUrlRequestDTO requestDTO, String jwtToken);

  void checkForVerifyEmailErrors(String token);

  void checkForUserStatsErrors(Integer page);

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

  void throwLinkNotAccessible();

  void throwUsernameNotFound();

  void throwUserAlreadyVerified();

  void throwException(String message);
}
