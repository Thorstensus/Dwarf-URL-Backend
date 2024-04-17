package org.chrenko.andrej.urlshortenerapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.chrenko.andrej.urlshortenerapp.dto.linkstats.LinkStatPageDTO;
import org.chrenko.andrej.urlshortenerapp.dto.userstats.UserStatPageDTO;
import org.chrenko.andrej.urlshortenerapp.services.ShortenedURLService;
import org.chrenko.andrej.urlshortenerapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdminController {

  private final UserService userService;

  private final ShortenedURLService shortenedURLService;

  @Autowired
  public AdminController(UserService userService, ShortenedURLService shortenedURLService) {
    this.userService = userService;
    this.shortenedURLService = shortenedURLService;
  }

  @Operation(summary = "Endpoint which ranks the users by their total shortened link clicks. The results are paginated with 10 results loaded per page.")
  @GetMapping("/user-stats")
  public ResponseEntity<UserStatPageDTO> getUserStatistics(
      @RequestParam(required = false, defaultValue = "1") String page) {
    return ResponseEntity.status(200).body(userService.getUserStats(Integer.parseInt(page)));
  }

  @Operation(summary = "Endpoint which ranks the shortened links by their visit count. The results are paginated with 10 results loaded per page.")
  @GetMapping("/link-stats")
  public ResponseEntity<LinkStatPageDTO> getLinkStatistics(
      @RequestParam(required = false, defaultValue = "1") String page) {
    return ResponseEntity.status(200).body(shortenedURLService.getLinkStats(Integer.parseInt(page)));
  }
}
