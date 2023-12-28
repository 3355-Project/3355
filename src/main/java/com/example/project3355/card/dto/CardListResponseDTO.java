package com.example.project3355.card.dto;

import com.example.project3355.user.dto.UserInfoResponseDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class CardListResponseDTO {
    private UserInfoResponseDto user;
    private List<CardResponseDTO> cardList;

    public CardListResponseDTO(UserInfoResponseDto user, List<CardResponseDTO> cardList) {
        this.user = user;
        this.cardList = cardList;
    }
}
