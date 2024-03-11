package org.chrenko.andrej.urlshortenerapp.DTOs.Registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.chrenko.andrej.urlshortenerapp.DTOs.Abstract.ResponseDTO;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponseDTO extends ResponseDTO {

  private Long id;

  private String email;

  private boolean isAdmin;
}
