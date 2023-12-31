package com.example.project3355.card.dto;

import com.example.project3355.card.entity.Card;
import com.example.project3355.comment.dto.CommentResponseDto;
import com.example.project3355.global.common.CommonResponseDto;
import com.example.project3355.user.dto.UserInfoResponseDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class CardResponseDTO extends CommonResponseDto {
    private Long id;
    private String cardTitle;
    private String cardColor;
    private String cardDescription;
    private LocalDateTime deadline;
    private UserInfoResponseDto user;
    private String worker;
    private Integer sequence;
    private List<CommentResponseDto> commentList = new ArrayList<>();
    private List<WatchResponseDto> watchList = new ArrayList<>();

    public CardResponseDTO(String msg, Integer statusCode) {
        super(msg, statusCode);
    }

    public CardResponseDTO(Card card) {
        this.id = card.getId();
        this.cardTitle = card.getCardTitle();
        this.cardColor = card.getCardColor();
        this.cardDescription = card.getCardDescription();
        this.deadline = card.getDeadline();
        this.worker = card.setWorker();
        this.user = new UserInfoResponseDto(card.getUser());
        this.sequence=card.getSequence();
    }

    public CardResponseDTO(Card card, List<CommentResponseDto> commentList,List<WatchResponseDto> watchList){
        this.cardTitle=card.getCardTitle();
        this.cardColor=card.getCardColor();
        this.cardDescription=card.getCardDescription();
        this.deadline=card.getDeadline();
        this.commentList=commentList;
        this.watchList=watchList;
    }
}
