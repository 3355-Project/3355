package com.example.project3355.user.controller;

import com.example.project3355.global.common.CommonResponseDto;
import com.example.project3355.global.exception.common.BusinessException;
import com.example.project3355.global.jwt.JwtUtil;
import com.example.project3355.user.UserDetailsImpl;
import com.example.project3355.user.dto.UserInfoResponseDto;
import com.example.project3355.user.dto.UserLoginRequestDto;
import com.example.project3355.user.dto.UserProfileUpdateRequestDto;
import com.example.project3355.user.dto.UserSignupRequestDto;
import com.example.project3355.user.entity.User;
import com.example.project3355.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  private final JwtUtil jwtUtil;

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

  @PostMapping("/login")
  public ResponseEntity<?> login(
      @RequestBody UserLoginRequestDto requestDto, HttpServletResponse response) {
    try {
      userService.login(requestDto);
    } catch (BusinessException be) {
      return ResponseEntity.status(be.getStatus())
          .body(new CommonResponseDto(be.getMessage(), be.getStatus()));
    }

    // 로그인 시 헤더에 JWT 토큰이 보임
    response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(requestDto.getUsername()));

    return ResponseEntity.ok()
        .body(new CommonResponseDto("로그인 성공", HttpStatus.OK.value()));
  }

  @GetMapping("/profile/{userId}")
  public ResponseEntity<?> getProfile(@PathVariable Long userId) {

    try {
      UserInfoResponseDto responseDto = userService.getProfile(userId);
      return ResponseEntity.ok().body(responseDto);
    } catch (BusinessException be) {
      return ResponseEntity.status(be.getStatus())
          .body(new CommonResponseDto(be.getMessage(), be.getStatus()));
    }
  }

  @PatchMapping("/profile/{userId}")
  public ResponseEntity<?> updateProfile(
      @PathVariable Long userId,
      @Valid @RequestBody UserProfileUpdateRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    User loginUser = userDetails.getUser();

    try {
      userService.updateProfile(userId, requestDto, loginUser);
      return ResponseEntity.ok()
          .body(new CommonResponseDto("프로필 수정 성공", HttpStatus.OK.value()));
    } catch (BusinessException be) {
      return ResponseEntity.status(be.getStatus())
          .body(new CommonResponseDto(be.getMessage(), be.getStatus()));
    }
  }

}
