package org.chrenko.andrej.urlshortenerapp.dto.urlshortener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chrenko.andrej.urlshortenerapp.dto.abstractdto.ResponseDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortenedUrlResponseDTO extends ResponseDTO {

  private String shortUrl;
}
