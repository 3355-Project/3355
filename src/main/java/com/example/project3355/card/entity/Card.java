package com.example.project3355.card.entity;

import com.example.project3355.card.dto.CardRequestDTO;
import com.example.project3355.card.dto.CardSequenceDTO;
import com.example.project3355.coulmn.dto.ColumnsSequenceDto;
import com.example.project3355.coulmn.entity.Columns;
import com.example.project3355.user.entity.User;
import jakarta.persistence.*;
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
    private Integer sequence;

    // 컬럼
    @ManyToOne
    @JoinColumn(name = "column_id")
    private Columns columns;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //작업자
    @ManyToOne
    @JoinColumn(name = "worker_id")
    private User worker; // User 엔티티와의 관계 설정

    // TODO: 마감시간

    @Builder
    public Card(String cardTitle, String cardColor , String cardDescription, User user, User worker) {
        this.cardTitle = cardTitle;
        this.cardColor = cardColor;
        this.cardDescription = cardDescription;
        this.user = user; // 로그인한 사용자를 작업자로 등록
        this.worker = worker; // 전달받은 worker를 작업자로 등록
    }

    public Card(CardRequestDTO dto) {
        this.cardTitle = dto.getCardTitle();
        this.cardColor = dto.getCardColor();
        this.cardDescription = dto.getCardDescription();
        this.worker = worker; // 전달받은 worker를 작업자로 등록
    }

    public void addSequence(CardSequenceDTO sequenceDto){
        this.sequence=sequenceDto.getSequence();
    }

}
