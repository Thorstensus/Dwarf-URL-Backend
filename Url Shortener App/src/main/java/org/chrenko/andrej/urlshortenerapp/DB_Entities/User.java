package org.chrenko.andrej.urlshortenerapp.DB_Entities;

import jakarta.persistence.*;
import lombok.*;
import org.chrenko.andrej.urlshortenerapp.Enum.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

  private boolean enabled;
  private boolean locked;
  private boolean verified;
  private boolean nonExpired;
  private boolean credentialsNonExpired;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private RefreshToken refreshToken;

  @OneToMany(mappedBy = "creator")
  private List<ShortenedURL> urls;

  public User(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = Role.USER;
    this.enabled = true;
    this.locked = false;
    this.verified = false;
    this.nonExpired = true;
    this.credentialsNonExpired = true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return nonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public String toString() {
    return "User{" +
        "enabled=" + enabled +
        ", locked=" + locked +
        ", verified=" + verified +
        ", nonExpired=" + nonExpired +
        ", credentialsNonExpired=" + credentialsNonExpired +
        ", id=" + id +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", role=" + role +
        ", refreshToken=" + refreshToken.getId() +
        ", urls=" + urls +
        '}';
  }
}
