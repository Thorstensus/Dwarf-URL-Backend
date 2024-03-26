package org.chrenko.andrej.urlshortenerapp.Controllers;

import org.chrenko.andrej.urlshortenerapp.DTOs.UrlShortener.ShortenedUrlRequestDTO;
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
  public ResponseEntity<Object> createShortenedUrl(@RequestBody ShortenedUrlRequestDTO requestDTO,
                                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String requestHeader) {
    return ResponseEntity.status(200).body(urlShortenerService.createShortenedUrl(requestDTO,
        jwtService.extractBearerToken(requestHeader)));
  }

  @GetMapping("/{code}")
  public ResponseEntity<Object> redirectToRealUrl(@PathVariable String code) {
    return ResponseEntity.status(302)
        .header("Location", urlShortenerService.getRealUrl(code))
        .build();
  }
}
