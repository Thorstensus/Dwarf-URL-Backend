package org.chrenko.andrej.urlshortenerapp.DTOs.UserStats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chrenko.andrej.urlshortenerapp.DTOs.Abstract.ResponseDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatDTO extends ResponseDTO {

  private Long rank;

  private Long userId;

  private String userMail;

  private Long totalClicks;
}
