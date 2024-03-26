package org.chrenko.andrej.urlshortenerapp.DTOs.Authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDTO {

  private String email;

  private String password;
}
