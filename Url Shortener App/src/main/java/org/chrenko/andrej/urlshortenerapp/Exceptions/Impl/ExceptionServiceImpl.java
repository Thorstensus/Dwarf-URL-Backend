package org.chrenko.andrej.urlshortenerapp.Exceptions.Impl;

import jakarta.servlet.http.HttpServletRequest;
import org.chrenko.andrej.urlshortenerapp.DB_Entities.User;
import org.chrenko.andrej.urlshortenerapp.DTOs.Authentication.AuthenticationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Refresh_Access_Token.RefreshAccessRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.UrlShortener.DeleteShortenedUrlRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.UrlShortener.ShortenedUrlRequestDTO;
import org.chrenko.andrej.urlshortenerapp.Exceptions.DTOs.ApiRequestException;
import org.chrenko.andrej.urlshortenerapp.Exceptions.ExceptionService;
import org.chrenko.andrej.urlshortenerapp.Repositories.RefreshTokenRepository;
import org.chrenko.andrej.urlshortenerapp.Repositories.ShortenedURLRepository;
import org.chrenko.andrej.urlshortenerapp.Repositories.UserRepository;
import org.chrenko.andrej.urlshortenerapp.Security.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class ExceptionServiceImpl implements ExceptionService {

  private final UserRepository userRepository;

  private final HttpServletRequest httpServletRequest;

  private final PasswordEncoder passwordEncoder;

  private final RefreshTokenRepository refreshTokenRepository;

  private final JwtService jwtService;

  private final ShortenedURLRepository shortenedURLRepository;

  @Autowired
  public ExceptionServiceImpl(UserRepository userRepository, HttpServletRequest httpServletRequest, PasswordEncoder passwordEncoder, RefreshTokenRepository refreshTokenRepository, JwtService jwtService, ShortenedURLRepository shortenedURLRepository) {
    this.userRepository = userRepository;
    this.httpServletRequest = httpServletRequest;
    this.passwordEncoder = passwordEncoder;
    this.refreshTokenRepository = refreshTokenRepository;
    this.jwtService = jwtService;
    this.shortenedURLRepository = shortenedURLRepository;
  }

  @Override
  public void checkForRegisterErrors(RegistrationRequestDTO requestDTO) {
    if (requestDTO == null || requestDTO.getPassword() == null || requestDTO.getName() == null || requestDTO.getEmail() == null) {
      throwAllFieldsRequired();
    } else if (!isValidEmailAddress(requestDTO.getEmail())) {
      throwNotValidEmailAddress();
    } else if (userRepository.existsByEmail(requestDTO.getEmail())) {
      throwEmailUsed();
    } else if (userRepository.existsByName(requestDTO.getName())) {
      throwUsernameUsed();
    } else if (requestDTO.getPassword().length() < 8) {
      throwPasswordTooShort();
    } else if (!isSafePassword(requestDTO.getPassword())) {
      throwPasswordNotSafe();
    }
  }

  @Override
  public void checkForAuthenticationErrors(AuthenticationRequestDTO requestDTO) {
    if (requestDTO == null || requestDTO.getEmail() == null || requestDTO.getPassword() == null) {
      throwAllFieldsRequired();;
    } else if (!isValidEmailAddress(requestDTO.getEmail())) {
      throwNotValidEmailAddress();
    } else {
      Optional<User> foundUser = userRepository.findByEmail(requestDTO.getEmail());
      if (foundUser.isEmpty() || !passwordEncoder.matches(requestDTO.getPassword(), foundUser.get().getPassword())) {
        throwUserOrEmailNotMatch();
      }
    }
  }

  @Override
  public void checkForRefreshTokenErrors(RefreshAccessRequestDTO requestDTO) {
    if (refreshTokenRepository.findRefreshTokenById(requestDTO.getUuid()).isEmpty()) {
      throwRefreshTokenNotExists();
    }
  }

  @Override
  public void checkForCreateShortenedUrlErrors(ShortenedUrlRequestDTO requestDTO, String jwtToken) {
    if (userRepository.findByEmail(jwtService.extractUsername(jwtToken)).isEmpty()) {
      throwJwtTokenInvalid();
    } else if (!isValidLink(requestDTO.getLongUrl())) {
      throwInvalidLink();
    }
  }

  @Override
  public void checkForDeleteShortenedUrlErrors(DeleteShortenedUrlRequestDTO requestDTO, String jwtToken) {
    if (userRepository.findByEmail(jwtService.extractUsername(jwtToken)).isEmpty()) {
      throwJwtTokenInvalid();
    } else if (shortenedURLRepository.findById(UUID.fromString(requestDTO.getShortenedUrlId())).isEmpty()
        || !shortenedURLRepository.findById(UUID.fromString(requestDTO.getShortenedUrlId())).get().getCreator().equals((userRepository.findByEmail(jwtService.extractUsername(jwtToken)).get()))) {
      throwLinkNotAccessible();
    }
  }

  @Override
  public void checkForVerifyEmailErrors(String token) {
    Optional<User> user = userRepository.findByEmail(jwtService.extractUsername(token));
    if (user.isEmpty()) {
      throwUsernameNotFound();
    } else if (user.get().isVerified()) {
      throwUserAlreadyVerified();
    }
  }

  @Override
  public boolean isValidEmailAddress(String email) {
    String regex = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b";
    return Pattern.compile(regex).matcher(email).matches();
  }

  @Override
  public boolean isSafePassword(String password) {
    String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$";
    return Pattern.compile(regex).matcher(password).matches();
  }

  @Override
  public boolean isValidLink(String link) {
    String regex = "^(https?://)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}(\\S*)$";
    return Pattern.compile(regex).matcher(link).matches();
  }

  @Override
  public void throwNotValidEmailAddress() {
    throwException("Please enter a valid e-mail address");
  }

  @Override
  public void throwEmailUsed() {
    throwException("The entered e-mail is already in use");
  }


  @Override
  public void throwUsernameUsed() {
    throwException("The entered username is already in use");
  }

  @Override
  public void throwPasswordTooShort() {
    throwException("The password must contain at least 8 characters");
  }

  @Override
  public void throwAllFieldsRequired() {
    throwException("All fields must be filled");
  }

  @Override
  public void throwPasswordNotSafe() {
    throwException("The password must contain at least one uppercase letter, one lowercase letter, one number and one special character");
  }

  @Override
  public void throwUserOrEmailNotMatch() {
    throwException("The e-mail and password don't match");
  }

  @Override
  public void throwRefreshTokenNotExists() {
    throwException("Refresh Token does not exist");
  }

  @Override
  public void throwRefreshTokenExpired(String id) {
    throwException(id + "Refresh token is expired, please log in again");
  }

  @Override
  public void throwJwtTokenInvalid() {
    throwException("Unknown Error: Invalid Token");
  }

  @Override
  public void throwInvalidLink() {
    throwException("Please input a valid link");
  }

  @Override
  public void throwLinkNotAccessible() {
    throwException("Only links created by you can be deleted");
  }

  @Override
  public void throwUsernameNotFound() {
    throwException("User not found.");
  }

  @Override
  public void throwUserAlreadyVerified() {
    throwException("User has already been verified.");
  }
  @Override
  public void throwException(String message) {
    throw new ApiRequestException(httpServletRequest.getRequestURI(), message);
  }

}
