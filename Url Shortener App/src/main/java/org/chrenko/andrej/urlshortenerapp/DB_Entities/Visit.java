package org.chrenko.andrej.urlshortenerapp.DB_Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "ip_address")
  private String ip;

  private LocalDateTime time;

  @ManyToOne
  @JoinColumn(name = "url_code", nullable = false)
  private ShortenedURL shortenedURL;

  public Visit(String ip, ShortenedURL shortenedURL) {
    this.ip = ip;
    this.shortenedURL = shortenedURL;
    this.time = LocalDateTime.now();
  }

}
