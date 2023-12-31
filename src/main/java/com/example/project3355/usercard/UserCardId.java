package com.example.project3355.usercard;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class UserCardId implements Serializable {

  private Long userId;
  private Long cardId;

  public UserCardId(Long workerId, Long cardId) {
    this.userId = workerId;
    this.cardId = cardId;
  }

}