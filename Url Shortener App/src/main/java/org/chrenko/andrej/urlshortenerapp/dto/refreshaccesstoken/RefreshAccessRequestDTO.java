package org.chrenko.andrej.urlshortenerapp.dto.refreshaccesstoken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshAccessRequestDTO {

  private UUID uuid;
}
