package org.chrenko.andrej.urlshortenerapp.DTOs.UrlShortener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chrenko.andrej.urlshortenerapp.DTOs.Abstract.ResponseDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortenedUrlResponseDTO extends ResponseDTO {

  private String shortUrl;
}
