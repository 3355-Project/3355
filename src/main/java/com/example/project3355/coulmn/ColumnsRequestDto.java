package com.example.project3355.coulmn;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ColumnsRequestDto {
  @NotNull
  private String columnTitle;

}
