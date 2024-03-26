package org.chrenko.andrej.urlshortenerapp.Services;

import org.chrenko.andrej.urlshortenerapp.DTOs.UrlShortener.ShortenedUrlRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.UrlShortener.ShortenedUrlResponseDTO;

public interface UrlShortenerService {
  ShortenedUrlResponseDTO createShortenedUrl(ShortenedUrlRequestDTO requestDto, String jwtToken);

  String getRealUrl(String code);
}
