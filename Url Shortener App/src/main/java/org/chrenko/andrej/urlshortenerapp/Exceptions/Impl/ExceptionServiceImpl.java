package org.chrenko.andrej.urlshortenerapp.Exceptions.Impl;

import jakarta.servlet.http.HttpServletRequest;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.Exceptions.DTOs.ApiRequestException;
import org.chrenko.andrej.urlshortenerapp.Exceptions.ExceptionService;
import org.chrenko.andrej.urlshortenerapp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class ExceptionServiceImpl implements ExceptionService {

  private final UserRepository userRepository;

  private final HttpServletRequest httpServletRequest;

  @Autowired
  public ExceptionServiceImpl(UserRepository userRepository, HttpServletRequest httpServletRequest) {
    this.userRepository = userRepository;
    this.httpServletRequest = httpServletRequest;
  }

  @Override
  public void checkForRegisterErrors(RegistrationRequestDTO requestDTO) {
    if (requestDTO == null || requestDTO.getPassword() == null || requestDTO.getUsername() == null || requestDTO.getEmail() == null) {
      throwAllFieldsRequired();
    } else if (!isValidEmailAddress(requestDTO.getEmail())) {
      throwNotValidEmailAddress();
    } else if (userRepository.existsByEmail(requestDTO.getEmail())) {
      throwEmailUsed();
    } else if (userRepository.existsByUsername(requestDTO.getUsername())) {
      throwUsernameUsed();
    } else if (requestDTO.getPassword().length() < 8) {
      throwPasswordTooShort();
    } else if (!isSafePassword(requestDTO.getPassword())) {
      throwPasswordNotSafe();
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
  public void throwException(String message) {
    throw new ApiRequestException(httpServletRequest.getRequestURI(), message);
  }
}
