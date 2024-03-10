package org.chrenko.andrej.urlshortenerapp.DB_Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_tokens")
public class RefreshToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String token;

  private Date expiryDate;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  public RefreshToken(String token, Date expiryDate, User user) {
    this.token = token;
    this.expiryDate = expiryDate;
    this.user = user;
  }

  @Override
  public String toString() {
    return "RefreshToken{"
        + "id=" + id
        + ", token='" + token + '\''
        + ", expiryDate=" + expiryDate
        + ", user=" + (user != null ? user.getUsername() : null)
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RefreshToken that)) {
      return false;
    }
    return Objects.equals(getId(), that.getId()) && Objects.equals(getToken(), that.getToken()) && Objects.equals(getExpiryDate(), that.getExpiryDate()) && Objects.equals(getUser(), that.getUser());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getToken(), getExpiryDate(), getUser());
  }
}
