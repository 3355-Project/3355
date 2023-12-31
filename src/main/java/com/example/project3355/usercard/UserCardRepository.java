package com.example.project3355.usercard;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {

  void deleteByCardIdAndUserId(Long cardId, Long id);

}
