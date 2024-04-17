package org.chrenko.andrej.urlshortenerapp.dto.userstats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chrenko.andrej.urlshortenerapp.dto.abstractdto.ResponseDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatDTO extends ResponseDTO {

  private Long rank;

  private Long userId;

  private String userMail;

  private Long totalClicks;
}
