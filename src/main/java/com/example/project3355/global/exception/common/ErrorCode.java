package com.example.project3355.global.exception.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

  // jwt
  INVALID_JWT_SIGNATURE_EXCEPTION(401, "유효하지 않는 JWT 서명 입니다."),
  EXPIRED_JWT_TOKEN_EXCEPTION(401, "만료된 JWT token 입니다."),
  UNSUPPORTED_JWT_TOKEN_EXCEPTION(401, "지원되지 않는 JWT 토큰 입니다."),
  INVALID_JWT_TOKEN_EXCEPTION(401, "잘못된 JWT 토큰 입니다."),

  // user
  PASSWORD_MISMATCH_EXCEPTION(401, "비밀번호와 비밀번호 확인이 불일치 합니다."),
  ALREADY_EXIST_USERNAME_EXCEPTION(401, "중복된 유저네임입니다."),
  ALREADY_EXIST_EMAIL_EXCEPTION(401, "중복된 이메일입니다"),
  USER_NOT_FOUND_EXCEPTION(401, "존재하지 않는 유저입니다."),
  AUTHENTICATION_MISMATCH_EXCEPTION(401, "수정 및 삭제 권한이 없습니다.");

  // board

  // coulmn

  // card

  // comment

  private final int status;

  private final String message;

  ErrorCode(int status, String message) {
    this.status = status;
    this.message = message;
  }
}