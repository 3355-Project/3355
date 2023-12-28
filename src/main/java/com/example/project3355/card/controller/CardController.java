package com.example.project3355.card.controller;

import com.example.project3355.card.dto.CardListResponseDTO;
import com.example.project3355.card.dto.CardRequestDTO;
import com.example.project3355.card.dto.CardResponseDTO;
import com.example.project3355.card.service.CardService;
import com.example.project3355.global.common.CommonResponseDto;
import com.example.project3355.user.UserDetailsImpl;
import com.example.project3355.user.dto.UserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

@RequestMapping("/cards")
@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    // 카드 생성
    @PostMapping
    public ResponseEntity<CardResponseDTO> postCard(@RequestBody CardRequestDTO cardRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDTO responseDTO = cardService.createCard(cardRequestDTO, userDetails.getUser());

        return ResponseEntity.status(201).body(responseDTO);
    }

    // 카드 조회
    @GetMapping("/{cardId}")
    public ResponseEntity<CommonResponseDto> getCard(@PathVariable Long cardId) {
        try {
            CardResponseDTO responseDTO = cardService.getCardDto(cardId);
            return ResponseEntity.ok().body(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 카드 상세 조회
    @GetMapping
    public ResponseEntity<List<CardListResponseDTO>> getCardList() {
        List<CardListResponseDTO> response = new ArrayList<>();

        Map<UserInfoResponseDto, List<CardResponseDTO>> responseDTOMap = cardService.getUserCardMap();

        responseDTOMap.forEach((key, value) -> response.add(new CardListResponseDTO(key, value)));

        return ResponseEntity.ok().body(response);
    }

    // 카드 수정
    @PutMapping("/{cardId}")
    public ResponseEntity<CardResponseDTO> putCard(@PathVariable Long cardId, @RequestBody CardRequestDTO cardRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            CardResponseDTO responseDTO = cardService.updateCard(cardId, cardRequestDTO, userDetails.getUser());
            return ResponseEntity.ok().body(responseDTO);
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CardResponseDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // TODO: 카드 삭제
}
