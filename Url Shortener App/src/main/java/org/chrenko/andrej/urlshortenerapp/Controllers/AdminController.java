package org.chrenko.andrej.urlshortenerapp.Controllers;

import org.chrenko.andrej.urlshortenerapp.DTOs.LinkStats.LinkStatPageDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.UserStats.UserStatPageDTO;
import org.chrenko.andrej.urlshortenerapp.Services.ShortenedURLService;
import org.chrenko.andrej.urlshortenerapp.Services.UserService;
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

  @GetMapping("/user-stats")
  public ResponseEntity<UserStatPageDTO> getUserStatistics(
      @RequestParam(required = false, defaultValue = "1") String page) {
    return ResponseEntity.status(200).body(userService.getUserStats(Integer.parseInt(page)));
  }

  @GetMapping("/link-stats")
  public ResponseEntity<LinkStatPageDTO> getLinkStatistics(
      @RequestParam(required = false, defaultValue = "1") String page) {
    return ResponseEntity.status(200).body(shortenedURLService.getLinkStats(Integer.parseInt(page)));
  }
  //TODO: view links with most clicks
}
