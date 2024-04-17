package org.chrenko.andrej.urlshortenerapp.Services.Impl;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.ShortenedURL;
import org.chrenko.andrej.urlshortenerapp.DB_Entities.User;
import org.chrenko.andrej.urlshortenerapp.DB_Entities.Visit;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Registration.RegistrationResponseDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.UserStats.UserStatDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.UserStats.UserStatPageDTO;
import org.chrenko.andrej.urlshortenerapp.Email.EmailService;
import org.chrenko.andrej.urlshortenerapp.Exceptions.ExceptionService;
import org.chrenko.andrej.urlshortenerapp.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock private ExceptionService exceptionService;

  @Mock private UserRepository userRepository;

  @Mock private PasswordEncoder passwordEncoder;

  @Mock private EmailService emailService;
  @InjectMocks private UserServiceImpl userService;

  @Test
  void registerUserWorksAsIntended() {
    RegistrationRequestDTO requestDTO = new RegistrationRequestDTO("Johnny",
        "john@gmail.com",
        "secretPass1@");

    doNothing().when(exceptionService).checkForRegisterErrors(any(RegistrationRequestDTO.class));
    doReturn(null).when(userRepository).save(any(User.class));
    when(passwordEncoder.encode(any(String.class))).thenReturn("hashedPassword");
    doNothing().when(emailService).sendVerificationMail(any(User.class));

    RegistrationResponseDTO expectedResponseDTO = new RegistrationResponseDTO(null, "john@gmail.com", false);
    RegistrationResponseDTO actualResponseDTO = userService.registerUser(requestDTO);

    assertEquals(expectedResponseDTO,actualResponseDTO);
  }

  @Test
  void getUserStatsReturnsCorrectResponse() {

    int page = 1;
    int pageSize = 10;
    int userRank = 1;
    PageRequest pageable = PageRequest.of(page - 1, pageSize);
    List<User> users = createUsers(pageSize);
    Page<User> pageResult = new PageImpl<>(users, pageable, users.size());
    when(userRepository.findAllByTotalClicksDesc(pageable)).thenReturn(pageResult);

    UserStatPageDTO result = userService.getUserStats(page);

    assertNotNull(result);
    assertEquals(pageSize, result.getUserStatistics().size());
    for (UserStatDTO userStatDTO : result.getUserStatistics()) {
      assertEquals(userRank++, userStatDTO.getRank());
      assertEquals(20, userStatDTO.getTotalClicks());
    }
  }

  private List<User> createUsers(int pageSize) {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < pageSize; i++) {
      User user = new User();
      user.setId((long) i);
      user.setName("username" + i);
      user.setUrls(createShortenedURLs(pageSize));
      users.add(user);
    }
    return users;
  }

  private List<ShortenedURL> createShortenedURLs(int pageSize) {
    List<ShortenedURL> urls = new ArrayList<>();
    for (int i = 0; i < pageSize; i++) {
      ShortenedURL url = new ShortenedURL();
      url.setClicks(new ArrayList<>());
      for (int j = 0; j < 2; j++) {
        url.getClicks().add(new Visit());
      }
      urls.add(url);
    }
    return urls;
  }

}