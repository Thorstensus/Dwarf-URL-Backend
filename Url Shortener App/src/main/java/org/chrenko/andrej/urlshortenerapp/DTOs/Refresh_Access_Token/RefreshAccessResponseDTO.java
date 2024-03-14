package org.chrenko.andrej.urlshortenerapp.DTOs.Refresh_Access_Token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshAccessResponseDTO {

  private UUID uuid;

  private String accessToken;
}
