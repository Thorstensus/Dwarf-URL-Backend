package org.chrenko.andrej.urlshortenerapp.dto.urlshortener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chrenko.andrej.urlshortenerapp.dto.abstractdto.RequestDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortenedUrlRequestDTO extends RequestDTO {

  private String longUrl;
}
