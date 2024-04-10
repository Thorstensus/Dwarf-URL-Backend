package org.chrenko.andrej.urlshortenerapp.DTOs.UserStats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chrenko.andrej.urlshortenerapp.DTOs.Abstract.ResponseDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatPageDTO extends ResponseDTO {

  private List<UserStatDTO> userStatistics;
}
