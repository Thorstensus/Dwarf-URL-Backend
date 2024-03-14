package org.chrenko.andrej.urlshortenerapp.DB_Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visit {

  @Id
  @Column(name = "ip_address")
  private String ip;

  private int count;

  @ManyToOne
  @JoinColumn(name = "url_code", nullable = false)
  private ShortenedURL shortenedURL;

  public Visit(String ip, ShortenedURL shortenedURL) {
    this.ip = ip;
    this.shortenedURL = shortenedURL;
    this.count = 1;
  }
}
