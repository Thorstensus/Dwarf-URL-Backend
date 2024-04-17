package org.chrenko.andrej.urlshortenerapp.Services.Impl;

import jakarta.servlet.http.HttpServletRequest;
import org.chrenko.andrej.urlshortenerapp.DB_Entities.ShortenedURL;
import org.chrenko.andrej.urlshortenerapp.DB_Entities.User;
import org.chrenko.andrej.urlshortenerapp.DB_Entities.Visit;
import org.chrenko.andrej.urlshortenerapp.DTOs.UrlShortener.DeleteShortenedUrlRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.UrlShortener.ShortenedUrlRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.UrlShortener.ShortenedUrlResponseDTO;
import org.chrenko.andrej.urlshortenerapp.Exceptions.ExceptionService;
import org.chrenko.andrej.urlshortenerapp.Repositories.ShortenedURLRepository;
import org.chrenko.andrej.urlshortenerapp.Repositories.VisitRepository;
import org.chrenko.andrej.urlshortenerapp.Services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class UrlShortenerServiceImplTest {

  @Mock ShortenedURLRepository shortenedURLRepository;

  @Mock UserService userService;

  @Mock ExceptionService exceptionService;

  @Mock VisitRepository visitRepository;

  @InjectMocks UrlShortenerServiceImpl urlShortenerService;

  @Test
  void createShortenedUrlReturnsCorrectResponse() {
    ShortenedUrlRequestDTO sampleRequest = new ShortenedUrlRequestDTO("https://sample.com");
    String sampleJwt = "sampleJwt";
    User sampleUser = new User("randomName", "randomMail@random.com", "randomPass");
    String expectedPartialResponse = "http://localhost:8080/api/urls/";
    UUID sampleUUID = UUID.randomUUID();

    doNothing().when(exceptionService).checkForCreateShortenedUrlErrors(sampleRequest, sampleJwt);
    when(userService.extractUserFromToken(sampleJwt)).thenReturn(Optional.of(sampleUser));

    ArgumentCaptor<ShortenedURL> savedShortenedUrlCaptor = ArgumentCaptor.forClass(ShortenedURL.class);
    when(shortenedURLRepository.save(savedShortenedUrlCaptor.capture())).thenAnswer(invocation -> {
      ShortenedURL savedShortenedUrl = savedShortenedUrlCaptor.getValue();
      savedShortenedUrl.setCode(sampleUUID);
      return savedShortenedUrl;
    });


    ShortenedUrlResponseDTO actualResponse = urlShortenerService.createShortenedUrl(sampleRequest, sampleJwt);

    assertTrue(actualResponse.getShortUrl().contains(expectedPartialResponse));
  }

  @Test
  void redirectToRealUrlRedirectsCorrectly() {
    String code = "XpMg5yZrczvmXQf2GPhYgK";
    ShortenedURL shortenedURL = new ShortenedURL("https://example.com", new User());
    HttpServletRequest servletRequest = mock(HttpServletRequest.class);

    when(shortenedURLRepository.findById(any(UUID.class))).thenReturn(Optional.of(shortenedURL));

    String result = urlShortenerService.redirectToRealUrl(code, servletRequest);

    assertEquals("https://example.com", result);
  }

  @Test
  void redirectToIncorrectRealUrlRedirectsToRickroll() {
    String code = "XpMg5yZrczvmXQf2GPhYgK";
    HttpServletRequest servletRequest = mock(HttpServletRequest.class);
    when(shortenedURLRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

    String result = urlShortenerService.redirectToRealUrl(code, servletRequest);

    assertEquals("https://www.youtube.com/watch?v=dQw4w9WgXcQ", result);
  }

  @Test
  void deleteShortenedUrlWorksCorrectly() {
    DeleteShortenedUrlRequestDTO sampleRequest = new DeleteShortenedUrlRequestDTO(UUID.randomUUID().toString());
    String sampleJwt = "sampleJwt";
    ShortenedURL urlToDelete = new ShortenedURL();

    doNothing().when(exceptionService).checkForDeleteShortenedUrlErrors(sampleRequest, sampleJwt);
    when(shortenedURLRepository.findById(UUID.fromString(sampleRequest.getShortenedUrlId())))
        .thenReturn(Optional.of(urlToDelete));
    doNothing().when(shortenedURLRepository).delete(urlToDelete);

    String expectedMessage = "Successfully deleted";
    String actualMessage = urlShortenerService.deleteShortenedUrl(sampleRequest, sampleJwt);
    assertEquals(expectedMessage, actualMessage);
  }
}