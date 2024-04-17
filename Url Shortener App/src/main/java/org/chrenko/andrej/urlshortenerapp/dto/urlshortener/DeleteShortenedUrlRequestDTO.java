package org.chrenko.andrej.urlshortenerapp.dto.urlshortener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chrenko.andrej.urlshortenerapp.dto.abstractdto.ResponseDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteShortenedUrlRequestDTO extends ResponseDTO {

  private String shortenedUrlId;
}
