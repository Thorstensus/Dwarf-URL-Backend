package org.chrenko.andrej.urlshortenerapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.chrenko.andrej.urlshortenerapp.dto.urlshortener.DeleteShortenedUrlRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.urlshortener.ShortenedUrlRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.urlshortener.ShortenedUrlResponseDTO;
import org.chrenko.andrej.urlshortenerapp.security.services.JwtService;
import org.chrenko.andrej.urlshortenerapp.services.UrlShortenerService;
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

  @Operation(summary = "Endpoint used for creating the shortened URLs themselves.")
  @PostMapping("/shorten")
  public ResponseEntity<ShortenedUrlResponseDTO> createShortenedUrl(@RequestBody ShortenedUrlRequestDTO requestDTO,
                                                                    @RequestHeader(HttpHeaders.AUTHORIZATION) String requestHeader) {
    return ResponseEntity.status(200).body(urlShortenerService.createShortenedUrl(requestDTO,
        jwtService.extractBearerToken(requestHeader)));
  }

  @Operation(summary = "All generated shortened URLs are accessed through this endpoint.")
  @GetMapping("/{code}")
  public ResponseEntity<String> redirectToRealUrl(@PathVariable String code, HttpServletRequest servletRequest) {
    return ResponseEntity.status(302)
        .header("Location", urlShortenerService.redirectToRealUrl(code, servletRequest))
        .build();
  }

  @Operation(summary = "This endpoint allows users to delete their created URLs.")
  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteShortenedUrl(@RequestBody DeleteShortenedUrlRequestDTO requestDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String requestHeader) {
    return ResponseEntity.status(200).body(urlShortenerService.deleteShortenedUrl(requestDTO, jwtService.extractBearerToken(requestHeader)));
  }
}
