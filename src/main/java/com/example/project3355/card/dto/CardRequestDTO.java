package com.example.project3355.card.dto;

import com.example.project3355.user.entity.User;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class CardRequestDTO {
    private String cardTitle;
    private String cardColor;
    private String cardDescription;
}
