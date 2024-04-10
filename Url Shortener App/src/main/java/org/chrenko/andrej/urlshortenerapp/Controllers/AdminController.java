package org.chrenko.andrej.urlshortenerapp.Controllers;

import org.chrenko.andrej.urlshortenerapp.DTOs.UserStatPageDTO;
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

  @Autowired
  public AdminController(UserService userService) {
    this.userService = userService;
  }


  @GetMapping("/user-stats")
  public ResponseEntity<UserStatPageDTO> getUserStatistics(
      @RequestParam(required = false, defaultValue = "1") String page) {
    return ResponseEntity.status(200).body(userService.getUserStats(Integer.parseInt(page)));
  }
  //TODO: view users with most clicks, view links with most clicks
}
