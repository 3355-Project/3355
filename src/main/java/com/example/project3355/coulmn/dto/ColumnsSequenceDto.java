package com.example.project3355.coulmn.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ColumnsSequenceDto {
  private Integer sequence;


  public ColumnsSequenceDto(Integer sequence){
    this.sequence=sequence;
  }

}
