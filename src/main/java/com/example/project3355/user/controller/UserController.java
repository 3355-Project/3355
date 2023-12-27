package com.example.project3355.user.controller;

import com.example.project3355.global.common.CommonResponseDto;
import com.example.project3355.global.exception.common.BusinessException;
import com.example.project3355.user.dto.UserSignupRequestDto;
import com.example.project3355.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@Valid @RequestBody UserSignupRequestDto requestDto) {
    try {
      userService.signup(requestDto);
      return ResponseEntity.status(HttpStatus.CREATED.value())
          .body(new CommonResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    } catch (BusinessException be) {
      return ResponseEntity.status(be.getStatus())
          .body(new CommonResponseDto(be.getMessage(), be.getStatus()));
    }
  }

}
