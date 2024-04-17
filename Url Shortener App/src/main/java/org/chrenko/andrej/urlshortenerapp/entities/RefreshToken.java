package org.chrenko.andrej.urlshortenerapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_tokens")
public class RefreshToken {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private Date expiryDate;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  public RefreshToken(Date expiryDate, User user) {
    this.expiryDate = expiryDate;
    this.user = user;
  }

  @Override
  public String toString() {
    return "RefreshToken{"
        + "id=" + id
        + ", expiryDate=" + expiryDate
        + ", user=" + user.getUsername()
        + '}';
  }
}
