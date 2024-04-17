package org.chrenko.andrej.urlshortenerapp.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.chrenko.andrej.urlshortenerapp.dto.abstractdto.RequestDTO;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDTO extends RequestDTO {

  private String name;

  private String email;

  private String password;
}
