package org.chrenko.andrej.urlshortenerapp.DB_Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortenedURL {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "url_code")
  private UUID code;

  private String link;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User creator;

  @OneToMany(mappedBy = "shortenedURL")
  private List<Visit> clicks;

  public ShortenedURL(String link, User creator) {
    this.link = link;
    this.creator = creator;
    this.clicks = new ArrayList<>();
  }
}
