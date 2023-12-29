package com.example.project3355.card.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CardSequenceDTO {
    private Integer sequence;

    public CardSequenceDTO(Integer sequence){
        this.sequence=sequence;
    }
}
