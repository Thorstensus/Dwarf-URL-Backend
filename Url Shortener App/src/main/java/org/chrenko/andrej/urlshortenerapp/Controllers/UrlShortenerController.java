package org.chrenko.andrej.urlshortenerapp.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.chrenko.andrej.urlshortenerapp.DTOs.UrlShortener.DeleteShortenedUrlRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.UrlShortener.ShortenedUrlRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.UrlShortener.ShortenedUrlResponseDTO;
import org.chrenko.andrej.urlshortenerapp.Security.Services.JwtService;
import org.chrenko.andrej.urlshortenerapp.Services.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/urls")
public class UrlShortenerController {

  private final UrlShortenerService urlShortenerService;

  private final JwtService jwtService;

  @Autowired
  public UrlShortenerController(UrlShortenerService urlShortenerService, JwtService jwtService) {
    this.urlShortenerService = urlShortenerService;
    this.jwtService = jwtService;
  }

  @PostMapping("/shorten")
  public ResponseEntity<ShortenedUrlResponseDTO> createShortenedUrl(@RequestBody ShortenedUrlRequestDTO requestDTO,
                                                                    @RequestHeader(HttpHeaders.AUTHORIZATION) String requestHeader) {
    return ResponseEntity.status(200).body(urlShortenerService.createShortenedUrl(requestDTO,
        jwtService.extractBearerToken(requestHeader)));
  }

  @GetMapping("/{code}")
  public ResponseEntity<String> redirectToRealUrl(@PathVariable String code, HttpServletRequest servletRequest) {
    return ResponseEntity.status(302)
        .header("Location", urlShortenerService.redirectToRealUrl(code, servletRequest))
        .build();
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteShortenedUrl(@RequestBody DeleteShortenedUrlRequestDTO requestDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String requestHeader) {
    return ResponseEntity.status(200).body(urlShortenerService.deleteShortenedUrl(requestDTO, jwtService.extractBearerToken(requestHeader)));
  }

  //todo mail sending, link expiration
}
