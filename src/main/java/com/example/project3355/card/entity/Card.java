package com.example.project3355.card.entity;

import com.example.project3355.card.dto.CardRequestDTO;
import com.example.project3355.user.entity.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String cardTitle;

    @Column(nullable = false)
    String cardColor;

    @Column(nullable = false)
    String cardDescription;

    @Column(nullable = false)
    LocalDateTime deadline = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // TODO: 작업자

    @Builder
    public Card(String cardTitle, String cardColor , String cardDescription) {
        this.cardTitle = cardTitle;
        this.cardColor = cardColor;
        this.cardDescription = cardDescription;
    }

    public Card(CardRequestDTO dto) {
        this.cardTitle = dto.getCardTitle();
        this.cardColor = dto.getCardColor();
        this.cardDescription = dto.getCardDescription();
        this.deadline = dto.getDeadline();
    }
}
