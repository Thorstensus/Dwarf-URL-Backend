package org.chrenko.andrej.urlshortenerapp.Exceptions.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
