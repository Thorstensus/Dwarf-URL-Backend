package org.chrenko.andrej.urlshortenerapp.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponseDTO {

  private Long id;

  private String email;

  private boolean isAdmin;
}
