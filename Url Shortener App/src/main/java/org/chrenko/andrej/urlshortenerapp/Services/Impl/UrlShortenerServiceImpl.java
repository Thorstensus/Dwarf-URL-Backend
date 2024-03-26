package org.chrenko.andrej.urlshortenerapp.Services.Impl;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.ShortenedURL;
import org.chrenko.andrej.urlshortenerapp.DB_Entities.User;
import org.chrenko.andrej.urlshortenerapp.DTOs.UrlShortener.ShortenedUrlRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.UrlShortener.ShortenedUrlResponseDTO;
import org.chrenko.andrej.urlshortenerapp.Exceptions.ExceptionService;
import org.chrenko.andrej.urlshortenerapp.Repositories.ShortenedURLRepository;
import org.chrenko.andrej.urlshortenerapp.Services.UrlShortenerService;
import org.chrenko.andrej.urlshortenerapp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unbrokendome.base62.Base62;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

  private final ShortenedURLRepository shortenedURLRepository;

  private final UserService userService;

  private final ExceptionService exceptionService;

  @Autowired
  public UrlShortenerServiceImpl(ShortenedURLRepository shortenedURLRepository, UserService userService, ExceptionService exceptionService) {
    this.shortenedURLRepository = shortenedURLRepository;
    this.userService = userService;
    this.exceptionService = exceptionService;
  }

  @Override
  public ShortenedUrlResponseDTO createShortenedUrl(ShortenedUrlRequestDTO requestDto, String jwtToken) {
    exceptionService.checkForCreateShortenedUrlErrors(requestDto, jwtToken);
    User creator = userService.extractUserFromToken(jwtToken).get();
    ShortenedURL shortenedURL = new ShortenedURL(requestDto.getLongUrl(), creator);
    shortenedURLRepository.save(shortenedURL);
    String encodedUUID = Base62.encodeUUID(shortenedURL.getCode());
    String shortenedUrl = "http://localhost:8080/api/urls/" + encodedUUID;
    return new ShortenedUrlResponseDTO(shortenedUrl);
  }

  @Override
  public String getRealUrl(String code) {
    try {
      UUID uuid = Base62.decodeUUID(code);
      Optional<ShortenedURL> url = shortenedURLRepository.findById(uuid);
      if (url.isPresent()) {
        return url.get().getLink();
      } else {
        return "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
      }
    } catch (IllegalArgumentException e) {
      return "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
    }
  }

}
