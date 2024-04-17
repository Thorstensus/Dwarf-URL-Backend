package org.chrenko.andrej.urlshortenerapp.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.chrenko.andrej.urlshortenerapp.exceptions.dto.ApiRequestException;
import org.chrenko.andrej.urlshortenerapp.exceptions.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({AccessDeniedException.class})
  @ResponseBody
  public ResponseEntity<Object> handleAuthenticationException(HttpServletRequest httpServletRequest) {
    return new ResponseEntity<>(new ApiRequestException(httpServletRequest.getRequestURI(), "Unauthorized Access"),
        new HttpHeaders(),
        HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler({ExpiredJwtException.class})
  @ResponseBody
  public ResponseEntity<Object> handleExpiredTokenException(HttpServletRequest httpServletRequest) {
    return new ResponseEntity<>(new ApiRequestException(httpServletRequest.getRequestURI(), "Token Expired"),
        new HttpHeaders(),
        HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(value = {ApiRequestException.class})
  public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
    ErrorResponse errorResponse =
        new ErrorResponse(
            HttpStatus.BAD_REQUEST,
            e.getEndpoint(),
            e.getMessage(),
            ZonedDateTime.now(ZoneId.of("Europe/Bratislava")));
    return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }
}
