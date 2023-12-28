package com.example.project3355.board.controller;


import com.example.project3355.board.dto.BoardRequestDto;
import com.example.project3355.board.dto.BoardResponseDto;
import com.example.project3355.board.entity.Board;
import com.example.project3355.board.repository.BoardRepository;
import com.example.project3355.board.service.BoardService;
import com.example.project3355.global.common.CommonResponseDto;
import com.example.project3355.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    // 작성
    @PostMapping("")
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto boardRequestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardResponseDto responseDto = boardService.createBoard(boardRequestDto, userDetails.getUser());

        return ResponseEntity.status(201).body(responseDto);
    }

    // 전체 조회
    @GetMapping("")
    public List<BoardResponseDto> getOwnPostList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            List<Board> ownPosts;
            if (userDetails == null) {
                ownPosts = boardRepository.findTop10ByOrderByCreatedAtDesc();
            } else {
                ownPosts = boardRepository.findAllByUserIdOrderByCreatedAtDesc(userDetails.getUser().getId());
            }
            return convertToDtoList(ownPosts);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    private List<BoardResponseDto> convertToDtoList(List<Board> boards) {
        return boards.stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
    }

    // 선택 조회
    @ResponseBody
    @GetMapping("{boardId}")
    public BoardResponseDto getBoard(@PathVariable Long boardId) {
        return boardService.getBoard(boardId);
    }

    // 수정
    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long boardId,
                                                        @RequestBody BoardRequestDto boardRequestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            BoardResponseDto updatedBoard = boardService.updateBoard(boardId, boardRequestDto, userDetails);
            return ResponseEntity.ok(updatedBoard);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long boardId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            String result = boardService.deleteBoard(boardId, userDetails);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("유효하지 않은 요청입니다.");
        }
    }

    // 초대
    @PostMapping("/{boardId}/invite/{invitedUserId}")
    public ResponseEntity<CommonResponseDto> inviteUserToBoard(@PathVariable Long boardId,
                                                               @PathVariable Long invitedUserId,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null && userDetails.getUser() != null) {
            Long userId = userDetails.getUser().getId();

            // 여기서 게시물 소유자를 확인하여 초대를 허용합니다
            Board board = boardService.findByBoard(boardId);
            if (board.getUser().getId().equals(userId)) { // 게시물 소유자를 확인하는 부분 수정
                if (userId.equals(invitedUserId)) {
                    return ResponseEntity.badRequest().body(new CommonResponseDto("자기 자신을 초대할 수 없습니다.", HttpStatus.BAD_REQUEST.value()));
                }
                boardService.inviteUserToBoard(userDetails.getUser(), boardId, invitedUserId);
                return ResponseEntity.ok().body(new CommonResponseDto("초대 완료되었습니다.", HttpStatus.OK.value()));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CommonResponseDto("게시물 소유자만 초대할 수 있습니다.", HttpStatus.FORBIDDEN.value()));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CommonResponseDto("인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED.value()));
    }

}
