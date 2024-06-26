package org.chrenko.andrej.urlshortenerapp.services.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.chrenko.andrej.urlshortenerapp.entities.ShortenedURL;
import org.chrenko.andrej.urlshortenerapp.entities.User;
import org.chrenko.andrej.urlshortenerapp.entities.Visit;
import org.chrenko.andrej.urlshortenerapp.dto.urlshortener.DeleteShortenedUrlRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.urlshortener.ShortenedUrlRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.urlshortener.ShortenedUrlResponseDTO;
import org.chrenko.andrej.urlshortenerapp.exceptions.ExceptionService;
import org.chrenko.andrej.urlshortenerapp.repositories.ShortenedURLRepository;
import org.chrenko.andrej.urlshortenerapp.repositories.VisitRepository;
import org.chrenko.andrej.urlshortenerapp.services.UrlShortenerService;
import org.chrenko.andrej.urlshortenerapp.services.UserService;
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

  private final VisitRepository visitRepository;

  @Autowired
  public UrlShortenerServiceImpl(ShortenedURLRepository shortenedURLRepository, UserService userService, ExceptionService exceptionService, VisitRepository visitRepository) {
    this.shortenedURLRepository = shortenedURLRepository;
    this.userService = userService;
    this.exceptionService = exceptionService;
    this.visitRepository = visitRepository;
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
  public String redirectToRealUrl(String code, HttpServletRequest servletRequest) {
    try {
      UUID uuid = Base62.decodeUUID(code);
      Optional<ShortenedURL> urlOptional = shortenedURLRepository.findById(uuid);
      if (urlOptional.isPresent()) {
        Visit newVisit = new Visit(servletRequest.getRemoteAddr(), urlOptional.get());
        visitRepository.save(newVisit);
        return urlOptional.get().getLink();
      } else {
        return "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
      }
    } catch (IllegalArgumentException e) {
      return "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
    }
  }

  @Override
  public String deleteShortenedUrl(DeleteShortenedUrlRequestDTO requestDTO, String jwtToken) {
    exceptionService.checkForDeleteShortenedUrlErrors(requestDTO, jwtToken);
    ShortenedURL urlToDelete = shortenedURLRepository.findById(UUID.fromString(requestDTO.getShortenedUrlId())).get();
    shortenedURLRepository.delete(urlToDelete);
    return "Successfully deleted";
  }

}
