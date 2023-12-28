package com.example.project3355.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserProfileUpdateRequestDto {

  @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z]{2,})+$",
      message = "올바른 이메일 주소 형식이 아닙니다.")
  private String email;

  private String introduce;

}
