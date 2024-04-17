package org.chrenko.andrej.urlshortenerapp.services;

import org.chrenko.andrej.urlshortenerapp.dto.linkstats.LinkStatPageDTO;

public interface ShortenedURLService {
  LinkStatPageDTO getLinkStats(int page);
}
