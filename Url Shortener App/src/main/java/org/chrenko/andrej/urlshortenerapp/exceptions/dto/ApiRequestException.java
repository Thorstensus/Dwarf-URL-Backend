package org.chrenko.andrej.urlshortenerapp.exceptions.dto;

import lombok.Getter;

@Getter
public class ApiRequestException extends RuntimeException {

  private final String endpoint;

  public ApiRequestException(String endpoint, String message) {
    super(message);
    this.endpoint = endpoint;
  }
}
