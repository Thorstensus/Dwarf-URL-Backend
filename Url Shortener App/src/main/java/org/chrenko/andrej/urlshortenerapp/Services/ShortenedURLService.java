package org.chrenko.andrej.urlshortenerapp.Services;

import org.chrenko.andrej.urlshortenerapp.DTOs.LinkStats.LinkStatPageDTO;

public interface ShortenedURLService {
  LinkStatPageDTO getLinkStats(int page);
}
