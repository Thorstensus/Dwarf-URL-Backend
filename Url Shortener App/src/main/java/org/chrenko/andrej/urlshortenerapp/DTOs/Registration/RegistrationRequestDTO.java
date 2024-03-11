package org.chrenko.andrej.urlshortenerapp.DTOs.Registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.chrenko.andrej.urlshortenerapp.DTOs.Abstract.RequestDTO;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDTO extends RequestDTO {

  private String username;

  private String email;

  private String password;
}
