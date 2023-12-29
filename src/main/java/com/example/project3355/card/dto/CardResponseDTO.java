package com.example.project3355.card.dto;

import com.example.project3355.card.entity.Card;
import com.example.project3355.global.common.CommonResponseDto;
import com.example.project3355.user.dto.UserInfoResponseDto;
import com.example.project3355.user.entity.User;
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
    private UserInfoResponseDto user;
    private User worker;  // 작업자

    //TODO: 마감시간

    public CardResponseDTO(String msg, Integer statusCode) {
        super(msg, statusCode);
    }

    public CardResponseDTO(Card card) {
        this.id = card.getId();
        this.cardTitle = card.getCardTitle();
        this.cardColor = card.getCardColor();
        this.cardDescription = card.getCardDescription();

        // 작업자 정보 추가
        if (card.getWorker() != null) {
            this.worker = new User(
                    card.getWorker().getUsername(),
                    card.getWorker().getPassword(),
                    card.getWorker().getEmail(),
                    card.getWorker().getIntroduce()
            );
        }

        this.user = new UserInfoResponseDto(card.getUser());
    }
}
