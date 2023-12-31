package com.example.project3355.coulmn.dto;


import com.example.project3355.card.dto.CardResponseDTO;
import com.example.project3355.coulmn.entity.Columns;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
  private Integer sequence;
  private List<CardResponseDTO> cardList = new ArrayList<>();

  public ColumnsResponseDto(Columns columns){
    this.columnTitle=columns.getColumnTitle();
    this.createdAt=columns.getCreatedAt();
    this.modifiedAt=columns.getModifiedAt();
    this.sequence=columns.getSequence();
  }

  public ColumnsResponseDto(Columns columns ,List<CardResponseDTO> cardList){
    this.columnTitle=columns.getColumnTitle();
    this.createdAt=columns.getCreatedAt();
    this.modifiedAt=columns.getModifiedAt();
    this.sequence=columns.getSequence();
    this.cardList=cardList;
  }

}
