package org.chrenko.andrej.urlshortenerapp.dto.linkstats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkStatPageDTO {
  private List<LinkStatDTO> linkStatistics;
}
