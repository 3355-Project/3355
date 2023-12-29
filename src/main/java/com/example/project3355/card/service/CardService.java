package com.example.project3355.card.service;

import com.example.project3355.card.dto.CardRequestDTO;
import com.example.project3355.card.dto.CardResponseDTO;
import com.example.project3355.card.dto.CardSequenceDTO;
import com.example.project3355.card.entity.Card;
import com.example.project3355.card.repository.CardRepository;
import com.example.project3355.global.exception.columns.ApiException;
import com.example.project3355.global.exception.common.ErrorCode;
import com.example.project3355.user.dto.UserInfoResponseDto;
import com.example.project3355.user.entity.User;
import com.example.project3355.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardResponseDTO createCard(CardRequestDTO dto, User user) {
        Card card = new Card(dto);
        card.setUser(user);

        var saved = cardRepository.save(card);

        return new CardResponseDTO(saved);
    }

    public CardResponseDTO getCardDto(Long cardId) {
        Card card = getCard(cardId);
        return new CardResponseDTO(card);
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
        User worker = getUserById(cardRequestDTO.getWorkerId()); // workerId를 User 엔티티로 변환
        Card card = getUserCard(cardId, user);

        card.setCardTitle(cardRequestDTO.getCardTitle());
        card.setCardDescription(cardRequestDTO.getCardDescription());
        card.setWorker(worker);

        return new CardResponseDTO(card);
    }


    public void deleteCard(Long cardId, User user) {
        Card card = getUserCard(cardId, user);

        cardRepository.delete(card);
    }

    public Card getCard(Long cardId) {

        return cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드 ID 입니다."));
    }

    public Card getUserCard(Long cardId, User user) {
        Card card = getCard(cardId);

        if(!user.getId().equals(card.getUser().getId())) {
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

        if (card.getSequence().equals(sequence)) {
            throw new ApiException(ErrorCode.INVALID_CARD_SEQUENCE);
        } else {
            // cardList를 사용하지 않고, 단일 카드에 대해서만 처리
            CardSequenceDTO sequenceDto = new CardSequenceDTO();
            card.addSequence(sequenceDto);
        }
    }

    // findById 메서드 정의
    public Card findById(Long id) {
        // 해당 ID에 대한 카드를 찾아서 반환하는 구현
        // 예시: 실제로는 데이터베이스에서 카드를 조회하게 됩니다.
        return cardRepository.findById(id).orElse(null);
    }


}