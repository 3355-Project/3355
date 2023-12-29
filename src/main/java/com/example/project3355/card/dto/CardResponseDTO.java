package com.example.project3355.card.dto;

import com.example.project3355.card.entity.Card;
import com.example.project3355.global.common.CommonResponseDto;
import com.example.project3355.user.dto.UserInfoResponseDto;
import java.time.LocalDateTime;
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
    private Integer sequence;


    public CardResponseDTO(String msg, Integer statusCode) {
        super(msg, statusCode);
    }

    public CardResponseDTO(Card card) {
        this.id = card.getId();
        this.cardTitle = card.getCardTitle();
        this.cardColor = card.getCardColor();
        this.cardDescription = card.getCardDescription();
        this.deadline = card.getDeadline();
        this.user = new UserInfoResponseDto(card.getUser());
        this.sequence=card.getSequence();
    }
}
