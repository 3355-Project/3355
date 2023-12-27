package com.example.project3355.coulmn;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ColumnsResponseDto {
  private String columnTitle;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  public ColumnsResponseDto(Columns columns){
    this.columnTitle=columns.getColumnTitle();
  }

}
