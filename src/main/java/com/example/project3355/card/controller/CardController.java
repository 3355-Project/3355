package com.example.project3355.card.controller;

import com.example.project3355.card.dto.CardListResponseDTO;
import com.example.project3355.card.dto.CardRequestDTO;
import com.example.project3355.card.dto.CardResponseDTO;
import com.example.project3355.card.repository.CardRepository;
import com.example.project3355.card.service.CardService;
import com.example.project3355.global.common.CommonResponseDto;
import com.example.project3355.user.UserDetailsImpl;
import com.example.project3355.user.dto.UserInfoResponseDto;
import com.example.project3355.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

        return ResponseEntity.ok(responseDTO);
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
            User worker;

            // 클라이언트에서 작업자 정보를 전달했는지 확인
            if (cardRequestDTO.getWorker() != null) {
                // 작업자 정보가 클라이언트에서 전달되었다면 해당 정보로 업데이트
                worker = cardRequestDTO.getWorker();
            } else {
                // 클라이언트에서 작업자 정보를 전달하지 않은 경우, 기존 작업자 정보를 사용
                CardResponseDTO existingCardDTO = cardService.getCardDto(cardId);
                worker = existingCardDTO.getWorker();  // 기존 작업자 정보 가져오기
            }

            CardResponseDTO responseDTO = cardService.updateCard(cardId, cardRequestDTO, worker);
            return ResponseEntity.ok().body(responseDTO);
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CardResponseDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 카드 삭제
    @DeleteMapping("/{cardId}")
    public ResponseEntity<CommonResponseDto> deleteCard(@PathVariable Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            cardService.deleteCard(cardId, userDetails.getUser());
            return ResponseEntity.ok().body(new CommonResponseDto("정상적으로 삭제 되었습니다.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
