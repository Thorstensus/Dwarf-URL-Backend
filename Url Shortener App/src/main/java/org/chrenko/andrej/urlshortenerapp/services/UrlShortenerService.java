package org.chrenko.andrej.urlshortenerapp.services;

import jakarta.servlet.http.HttpServletRequest;
import org.chrenko.andrej.urlshortenerapp.dto.urlshortener.DeleteShortenedUrlRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.urlshortener.ShortenedUrlRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.urlshortener.ShortenedUrlResponseDTO;

public interface UrlShortenerService {
  ShortenedUrlResponseDTO createShortenedUrl(ShortenedUrlRequestDTO requestDto, String jwtToken);

  String redirectToRealUrl(String code, HttpServletRequest request);

  String deleteShortenedUrl(DeleteShortenedUrlRequestDTO requestDTO, String jwtToken);
}
