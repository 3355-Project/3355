package com.example.project3355.card.dto;

import com.example.project3355.card.entity.Watch;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WatchResponseDto {

  private String username;

  public WatchResponseDto(Watch watch){
    this.username=watch.getUser().getUsername();
  }
}
