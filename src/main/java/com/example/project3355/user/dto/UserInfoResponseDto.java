package com.example.project3355.user.dto;

import com.example.project3355.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponseDto {

  private String username;
  private String email;
  private String introduce;

  public UserInfoResponseDto(User user) {
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.introduce = user.getIntroduce();
  }
}
