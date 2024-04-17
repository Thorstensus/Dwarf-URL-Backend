package org.chrenko.andrej.urlshortenerapp.services.impl;

import org.chrenko.andrej.urlshortenerapp.entities.ShortenedURL;
import org.chrenko.andrej.urlshortenerapp.dto.linkstats.LinkStatDTO;
import org.chrenko.andrej.urlshortenerapp.dto.linkstats.LinkStatPageDTO;
import org.chrenko.andrej.urlshortenerapp.repositories.ShortenedURLRepository;
import org.chrenko.andrej.urlshortenerapp.services.ShortenedURLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShortenedURLServiceImpl implements ShortenedURLService {

  private final ShortenedURLRepository shortenedURLRepository;

  @Autowired
  public ShortenedURLServiceImpl(ShortenedURLRepository shortenedURLRepository) {
    this.shortenedURLRepository = shortenedURLRepository;
  }

  @Override
  public LinkStatPageDTO getLinkStats(int page) {
    int realPageIndex = page - 1;
    List<LinkStatDTO> linkStats = new ArrayList<>();
    Pageable pageable = PageRequest.of(realPageIndex, 10);
    Page<ShortenedURL> chosenUrls = shortenedURLRepository.findShortenedURLSbyClickCount(pageable);
    int linkRank = realPageIndex * 10 + 1;
    for (ShortenedURL shortenedURL : chosenUrls) {
      LinkStatDTO currentLinkStat = new LinkStatDTO(
          linkRank,
          "http://localhost:8080/api/urls/" + shortenedURL.getCode(),
          shortenedURL.getLink(),
          shortenedURL.getClicks().size());
      linkStats.add(currentLinkStat);
      linkRank++;
    }
    return new LinkStatPageDTO(linkStats);
  }
}
