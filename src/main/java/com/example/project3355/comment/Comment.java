package com.example.project3355.comment;

import com.example.project3355.card.entity.Card;
import com.example.project3355.global.common.Timestamped;
import com.example.project3355.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Table(name="comments")
public class Comment extends Timestamped {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String comments;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "card_id", nullable = false)
  private Card card;

  public Comment(CommentRequestDto requestDto,Card card,User user){
    this.comments=requestDto.getComments();
    this.card=card;
    this.user=user;
  }

  public void update(CommentRequestDto requestDto){
    this.comments=requestDto.getComments();
  }



}
