package org.chrenko.andrej.urlshortenerapp.DTOs.UrlShortener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chrenko.andrej.urlshortenerapp.DTOs.Abstract.RequestDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortenedUrlRequestDTO extends RequestDTO {

  private String longUrl;
}
