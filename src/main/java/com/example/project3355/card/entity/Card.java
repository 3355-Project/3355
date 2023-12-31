package com.example.project3355.card.entity;

import com.example.project3355.card.dto.CardRequestDTO;
import com.example.project3355.card.dto.CardSequenceDTO;
import com.example.project3355.comment.entity.Comment;
import com.example.project3355.coulmn.dto.ColumnsSequenceDto;
import com.example.project3355.coulmn.entity.Columns;
import com.example.project3355.user.entity.User;
import com.example.project3355.usercard.UserCard;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
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
    @Column
    private Integer sequence;

    @Column(nullable = false)
    String cardTitle;

    @Column(nullable = false)
    String cardColor;

    @Column(nullable = false)
    String cardDescription;


    @Column(nullable = false)
    LocalDateTime deadline = LocalDateTime.now();

    @Column
    String worker;

  
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "card")
    private Set<UserCard> userCards = new HashSet<>();
  
    @ManyToOne
    @JoinColumn(name = "columns_id", nullable = false)
    private Columns columns;

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
    private List<Watch> watchList = new ArrayList<>();

    @Builder
    public Card(String cardTitle, String cardColor , String cardDescription) {
        this.cardTitle = cardTitle;
        this.cardColor = cardColor;
        this.cardDescription = cardDescription;
    }

    public Card(CardRequestDTO dto,Columns columns) {
        this.cardTitle = dto.getCardTitle();
        this.cardColor = dto.getCardColor();
        this.cardDescription = dto.getCardDescription();
        this.deadline = dto.getDeadline();
        this.columns=columns;
    }

    public void addSequence(CardSequenceDTO sequenceDto){
        this.sequence= sequenceDto.getSequence();
    }

    public String setWorker() {
        if (userCards != null && !userCards.isEmpty()) {
            // 여러 개의 username을 저장할 StringBuilder 초기화
            StringBuilder stringBuilder = new StringBuilder();

            // 각 UserCard를 반복하며 username을 StringBuilder에 추가
            for (UserCard userCard : userCards) {
                User worker = userCard.getUser();
                if (worker != null) {
                    stringBuilder.append(worker.getUsername()).append(", ");
                }
            }

            // 마지막의 쉼표와 공백 제거
            if (stringBuilder.length() > 0) {
                stringBuilder.setLength(stringBuilder.length() - 2);
            }

            // StringBuilder에 저장된 값을 worker 필드에 설정
            this.worker = stringBuilder.toString();
        }
        return worker;
    }

}
