package org.chrenko.andrej.urlshortenerapp.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDTO {

  private String status;

  private UUID refreshToken;

  private String accessToken;
}
