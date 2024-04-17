package org.chrenko.andrej.urlshortenerapp.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.chrenko.andrej.urlshortenerapp.dto.abstractdto.ResponseDTO;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponseDTO extends ResponseDTO {

  private Long id;

  private String email;

  private boolean isAdmin;
}
