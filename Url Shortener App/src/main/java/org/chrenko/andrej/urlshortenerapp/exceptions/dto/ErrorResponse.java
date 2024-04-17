package org.chrenko.andrej.urlshortenerapp.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

  private final HttpStatus httpStatus;

  private final String endpoint;

  private final String message;

  private final ZonedDateTime time;
}
