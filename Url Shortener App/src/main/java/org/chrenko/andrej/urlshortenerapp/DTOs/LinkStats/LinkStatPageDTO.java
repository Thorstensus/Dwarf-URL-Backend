package org.chrenko.andrej.urlshortenerapp.DTOs.LinkStats;

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
