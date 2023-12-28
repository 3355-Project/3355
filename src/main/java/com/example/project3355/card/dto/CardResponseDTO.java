package com.example.project3355.card.dto;

import com.example.project3355.card.entity.Card;
import com.example.project3355.global.common.CommonResponseDto;
import com.example.project3355.user.dto.UserInfoResponseDto;
import lombok.*;

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
    private UserInfoResponseDto user;

    //TODO: 작업자
    //TODO: 마감시간

    public CardResponseDTO(String msg, Integer statusCode) {
        super(msg, statusCode);
    }

    public CardResponseDTO(Card card) {
        this.id = card.getId();
        this.cardTitle = card.getCardTitle();
        this.cardColor = card.getCardColor();
        this.cardDescription = card.getCardDescription();
        this.user = new UserInfoResponseDto(card.getUser());
    }
}
