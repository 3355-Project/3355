package com.example.project3355.card.service;

import com.example.project3355.card.dto.CardRequestDTO;
import com.example.project3355.card.dto.CardResponseDTO;
import com.example.project3355.card.dto.CardSequenceDTO;
import com.example.project3355.card.dto.WatchResponseDto;
import com.example.project3355.card.entity.Card;
import com.example.project3355.card.entity.Watch;
import com.example.project3355.card.repository.CardRepository;
import com.example.project3355.comment.dto.CommentResponseDto;
import com.example.project3355.comment.entity.Comment;
import com.example.project3355.coulmn.entity.Columns;
import com.example.project3355.coulmn.repository.ColumnsRepository;
import com.example.project3355.global.exception.card.CardAssigneeAuthorizationException;
import com.example.project3355.global.exception.columns.ApiException;
import com.example.project3355.global.exception.common.ErrorCode;
import com.example.project3355.global.exception.user.UserNotFoundException;
import com.example.project3355.user.dto.UserInfoResponseDto;
import com.example.project3355.user.entity.User;
import com.example.project3355.user.repository.UserRepository;
import com.example.project3355.usercard.UserCard;
import com.example.project3355.usercard.UserCardId;
import com.example.project3355.usercard.UserCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CardService {

  private final CardRepository cardRepository;

  private final UserRepository userRepository;

  private final ColumnsRepository columnsRepository;

  private final UserCardRepository userCardRepository;


  public CardResponseDTO createCard(CardRequestDTO dto, User user, Long columnId) {
    Columns columns = columnsRepository.findById(dto.getColumnsId())
        .orElseThrow(() -> new ApiException(ErrorCode.INVALID_COLUMNS));
    Card card = new Card(dto, columns);
    card.setUser(user);


    CardSequenceDTO sequenceDto = new CardSequenceDTO(cardRepository.countByColumnsId(columnId).intValue()+1);
    card.addSequence(sequenceDto);
    Card saveCard = cardRepository.save(card);
    return new CardResponseDTO(saveCard);
  }

  public CardResponseDTO getCardDto(Long cardId) {
    Card card = getCard(cardId);
    List<CommentResponseDto> commentList = new ArrayList<>();
    List<WatchResponseDto> watchList = new ArrayList<>();
    for (Comment comment : card.getCommentList()) {
      commentList.add(new CommentResponseDto(comment));
    }
    for (Watch watch : card.getWatchList()) {
      watchList.add(new WatchResponseDto(watch));
    }
    return new CardResponseDTO(card, commentList, watchList);
  }

  public Map<UserInfoResponseDto, List<CardResponseDTO>> getUserCardMap() {
    Map<UserInfoResponseDto, List<CardResponseDTO>> userCardMap = new HashMap<>();

    List<Card> cardList = cardRepository.findAll();

    cardList.forEach(card -> {
      var userDto = new UserInfoResponseDto(card.getUser());
      var cardDto = new CardResponseDTO(card);
      if (userCardMap.containsKey(userDto)) {
        userCardMap.get(userDto).add(cardDto);
      } else {
        userCardMap.put(userDto, new ArrayList<>(List.of(cardDto)));
      }
    });

    return userCardMap;
  }

  @Transactional
  public CardResponseDTO updateCard(Long cardId, CardRequestDTO cardRequestDTO, User user) {
    Card card = getUserCard(cardId, user);

    card.setCardTitle(cardRequestDTO.getCardTitle());
    card.setCardDescription(cardRequestDTO.getCardDescription());
    card.setDeadline(cardRequestDTO.getDeadline());

    return new CardResponseDTO(card);
  }


  public void deleteCard(Long cardId, User user) {
    Card card = getUserCard(cardId, user);

    cardRepository.delete(card);
  }

  @Transactional
  public void assignmentWorker(Long workerId, Long cardId, User loginUser) {
    User worker = getWorker(workerId);
    Card card = getCard(cardId);

    // 카드 작성자만이 작업자를 할당
    if (!loginUser.getUsername().equals(card.getUser().getUsername())) {
      throw new CardAssigneeAuthorizationException();
    }

    UserCardId userCardId = new UserCardId(workerId, cardId);
    UserCard userCard = UserCard.builder()
        .id(userCardId)
        .user(worker)
        .card(card)
        .build();

    userCardRepository.save(userCard);
  }

  @Transactional
  public void deleteWorker(Long workerId, Long cardId, User loginUser) {
    User worker = getWorker(workerId);
    Card card = getCard(cardId);

    // 카드 작성자만이 작업자를 삭제
    if (!loginUser.getUsername().equals(card.getUser().getUsername())) {
      throw new CardAssigneeAuthorizationException();
    }

    userCardRepository.deleteByCardIdAndUserId(cardId, workerId);
  }

  private User getWorker(Long newWorkerId) {
    return userRepository.findById(newWorkerId).orElseThrow(UserNotFoundException::new);
  }

  public Card getCard(Long cardId) {

    return cardRepository.findById(cardId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드 ID 입니다."));
  }

  public Card getUserCard(Long cardId, User user) {
    Card card = getCard(cardId);

    if (!user.getId().equals(card.getUser().getId())) {
      throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
    }
    return card;
  }

  // 사용자 ID를 기반으로 사용자를 조회하는 메서드
  public User getUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 ID입니다."));
  }


  @Transactional
  public void sequenceCard(Long id, Integer sequence) {
    Card card = findById(id);


      CardSequenceDTO sequenceDto = new CardSequenceDTO(sequence);
      card.addSequence(sequenceDto);
  }

  // findById 메서드 정의
  public Card findById(Long id) {
    // 해당 ID에 대한 카드를 찾아서 반환하는 구현
    // 예시: 실제로는 데이터베이스에서 카드를 조회하게 됩니다.
    return cardRepository.findById(id).orElse(null);
  }

}