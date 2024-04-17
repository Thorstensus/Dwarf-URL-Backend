package org.chrenko.andrej.urlshortenerapp.Services.Impl;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.ShortenedURL;
import org.chrenko.andrej.urlshortenerapp.DB_Entities.Visit;
import org.chrenko.andrej.urlshortenerapp.DTOs.LinkStats.LinkStatDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.LinkStats.LinkStatPageDTO;
import org.chrenko.andrej.urlshortenerapp.Repositories.ShortenedURLRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShortenedURLServiceImplTest {

  @Mock ShortenedURLRepository shortenedURLRepository;

  @InjectMocks ShortenedURLServiceImpl shortenedURLService;

  @Test
  void getLinkStatsReturnsCorrectResponse() {
    int page = 1;
    int pageSize = 10;
    int linkRank = 1;
    PageRequest pageable = PageRequest.of(page - 1, pageSize);
    List<ShortenedURL> shortenedURLs = createShortenedURLs(pageSize);
    Page<ShortenedURL> pageResult = new PageImpl<>(shortenedURLs, pageable, shortenedURLs.size());
    when(shortenedURLRepository.findShortenedURLSbyClickCount(pageable)).thenReturn(pageResult);

    LinkStatPageDTO result = shortenedURLService.getLinkStats(page);

    assertNotNull(result);
    assertEquals(pageSize, result.getLinkStatistics().size());
    for (LinkStatDTO linkStatDTO : result.getLinkStatistics()) {
      assertTrue(linkStatDTO.getLink().contains("http://localhost:8080/api/urls/"));
      assertEquals("http://originalurl.com", linkStatDTO.getRedirectsTo());
      assertEquals(5, linkStatDTO.getTotalClicks());
      assertEquals(linkRank++, linkStatDTO.getTotalRank());
    }
  }

  private List<ShortenedURL> createShortenedURLs(int pageSize) {
    List<ShortenedURL> shortenedURLs = new ArrayList<>();
    for (int i = 0; i < pageSize; i++) {
      ShortenedURL shortenedURL = new ShortenedURL();
      shortenedURL.setCode(UUID.randomUUID());
      shortenedURL.setLink("http://originalurl.com");
      shortenedURL.setClicks(new ArrayList<>());
      for (int j = 0; j < 5; j++) {
        shortenedURL.getClicks().add(new Visit());
      }
      shortenedURLs.add(shortenedURL);
    }
    return shortenedURLs;
  }
}