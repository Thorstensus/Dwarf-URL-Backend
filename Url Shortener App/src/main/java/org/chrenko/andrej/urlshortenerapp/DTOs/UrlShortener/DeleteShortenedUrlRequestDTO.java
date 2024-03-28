package org.chrenko.andrej.urlshortenerapp.DTOs.UrlShortener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chrenko.andrej.urlshortenerapp.DTOs.Abstract.ResponseDTO;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteShortenedUrlRequestDTO extends ResponseDTO {

  private String shortenedUrlId;
}
