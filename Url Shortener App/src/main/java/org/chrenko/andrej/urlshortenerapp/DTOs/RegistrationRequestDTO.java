package org.chrenko.andrej.urlshortenerapp.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDTO {

  private String username;

  private String email;

  private String password;
}
