package com.example.project3355.card.controller;


import static com.example.project3355.global.exception.columns.ResponseCode.SUCCESS_WATCH;
import static com.example.project3355.global.exception.columns.ResponseCode.SUCCESS_WATCH_DELETE;

import com.example.project3355.card.service.WatchService;
import com.example.project3355.global.exception.columns.SuccessResponse;
import com.example.project3355.user.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class WatchController {
  private final WatchService watchService;

  @PostMapping("{id}/watch")
  public ResponseEntity<SuccessResponse> createWatch(@PathVariable Long id,
      @AuthenticationPrincipal UserDetailsImpl userDetails){
    watchService.createWatch(id,userDetails.getUser());
    return ResponseEntity.status(SUCCESS_WATCH.getHttpStatus()).body(new SuccessResponse(SUCCESS_WATCH));
  }
  @DeleteMapping("/watch/{id}")
  public ResponseEntity<SuccessResponse> deleteWatch(@PathVariable Long id,
      @AuthenticationPrincipal UserDetailsImpl userDetails){
    watchService.deleteWatch(id,userDetails.getUser());
    return ResponseEntity.status(SUCCESS_WATCH_DELETE.getHttpStatus()).body(new SuccessResponse(SUCCESS_WATCH_DELETE));
  }

}
