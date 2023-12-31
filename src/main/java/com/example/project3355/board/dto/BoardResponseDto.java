package com.example.project3355.board.dto;


import com.example.project3355.board.entity.Board;
import com.example.project3355.card.dto.CardResponseDTO;
import com.example.project3355.coulmn.dto.ColumnsResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardResponseDto {
    private Long boardId;
    private String boardTitle;
    private String user_id;
    private String boardColor;
    private String boardDescription;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<ColumnsResponseDto> columnsList =new ArrayList<>();


    public BoardResponseDto(Board board) {
        this.boardId = board.getId();
        this.boardTitle = board.getBoardTitle();
        this.user_id = board.getUser().getUsername();
        this.boardColor = board.getBoardColor();
        this.boardDescription = board.getBoardDescription();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

    public BoardResponseDto(Board board,List<ColumnsResponseDto> columnsList){
        this.boardId = board.getId();
        this.boardTitle=board.getBoardTitle();
        this.user_id = board.getUser().getUsername();
        this.boardColor=board.getBoardColor();
        this.boardDescription=board.getBoardDescription();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.columnsList = columnsList;
    }


}
