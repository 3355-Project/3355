package com.example.project3355.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserSignupRequestDto {

  @Pattern(regexp = "^[a-zA-Z0-9]{4,10}$",
      message = "유저네임은 알파벳 소문자와 대문자, 숫자만 입력가능합니다. 글자수는 4글자 이상 10글자 이하이어야 입니다. ")
  private String username;

  @Pattern(regexp = "^[a-zA-Z0-9]{4,}$",
      message = "비밀번호는 알파벳 소문자와 대문자, 숫자만 입력가능합니다. 글자수는 4글자 이상이어야 합니다.")
  private String password;

  private String checkPassword;

  @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z]{2,})+$",
      message = "올바른 이메일 주소 형식이 아닙니다.")
  private String email;

  private String introduce;
}