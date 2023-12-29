package com.example.project3355.card.entity;

import com.example.project3355.card.dto.CardRequestDTO;
import com.example.project3355.card.dto.CardSequenceDTO;
import com.example.project3355.comment.entity.Comment;
import com.example.project3355.coulmn.dto.ColumnsSequenceDto;
import com.example.project3355.coulmn.entity.Columns;
import com.example.project3355.user.entity.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
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
  
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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

}
