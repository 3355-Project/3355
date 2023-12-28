package com.example.project3355.board.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDto {
    @Size(min = 1, max = 50)
    private String boardTitle;
    @Size(min = 1, max = 50)
    private String boardColor;
    @Size(min = 1, max = 500)
    private String boardDescription;
}