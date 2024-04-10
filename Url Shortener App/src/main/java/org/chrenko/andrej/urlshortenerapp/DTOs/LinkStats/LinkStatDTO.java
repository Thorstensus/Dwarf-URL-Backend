package org.chrenko.andrej.urlshortenerapp.DTOs.LinkStats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkStatDTO {

  private int totalRank;

  private String link;

  private String redirectsTo;

  private int totalClicks;
}
