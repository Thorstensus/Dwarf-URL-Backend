package org.chrenko.andrej.urlshortenerapp.Security;

import org.chrenko.andrej.urlshortenerapp.Enum.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final CustomJwtAuthenticationEntryPoint customJwtAuthenticationEntryPoint;

  @Autowired
  public SecurityConfig(
      JwtAuthenticationFilter jwtAuthFilter,
      AuthenticationProvider authenticationProvider,
      CustomJwtAuthenticationEntryPoint customJwtAuthenticationEntryPoint) {
    this.jwtAuthFilter = jwtAuthFilter;
    this.authenticationProvider = authenticationProvider;
    this.customJwtAuthenticationEntryPoint = customJwtAuthenticationEntryPoint;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers("/api/users/register",
                        "/api/users/login",
                        "/api/users/refresh-token",
                        "/api/urls/**",
                        "/api/users/verification/**",
                        "/swagger/**")
                    .permitAll()
                    .requestMatchers("/api/urls/shorten")
                    .authenticated()
                    .requestMatchers("/api/admin/**")
                    .hasRole("ADMIN")
                    .anyRequest()
                    .authenticated())
        .sessionManagement(
            sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(
            exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(customJwtAuthenticationEntryPoint));
    return http.build();
  }
}