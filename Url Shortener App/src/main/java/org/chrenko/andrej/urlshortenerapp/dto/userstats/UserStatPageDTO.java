package org.chrenko.andrej.urlshortenerapp.dto.userstats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chrenko.andrej.urlshortenerapp.dto.abstractdto.ResponseDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatPageDTO extends ResponseDTO {

  private List<UserStatDTO> userStatistics;
}
