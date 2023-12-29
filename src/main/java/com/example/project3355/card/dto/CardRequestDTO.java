package com.example.project3355.card.dto;

import java.time.LocalDateTime;
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
    private LocalDateTime deadline;
}
