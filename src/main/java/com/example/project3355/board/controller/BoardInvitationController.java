package com.example.project3355.board.controller;

import com.example.project3355.board.entity.UserBoard;
import com.example.project3355.board.service.BoardService;
import com.example.project3355.global.common.CommonResponseDto;
import com.example.project3355.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardInvitationController {

    private final BoardService boardService;

    // Board 초대
    @PostMapping("/{boardId}/invite/{invitedUserId}")
    public ResponseEntity<CommonResponseDto> inviteUserToBoard(
            @PathVariable Long boardId,
            @PathVariable Long invitedUserId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            boardService.inviteUserToBoard(userDetails.getUser().getId(), boardId, invitedUserId);
            return ResponseEntity.ok().body(new CommonResponseDto("초대 완료되었습니다.", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CommonResponseDto("오류 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    // Board 초대된 사용자 리스트 조회
    @GetMapping("/{boardId}/inviteList")
    public ResponseEntity<List<String>> getInvitedUsersForBoard(
            @PathVariable Long boardId
    ) {
        try {
            List<UserBoard> invitedUserBoards = boardService.getInvitedUsersForBoard(boardId);
            List<String> invitedUsernames = invitedUserBoards.stream()
                    .map(userBoard -> userBoard.getUser().getUsername())
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(invitedUsernames);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList()); // Or handle the error accordingly
        }
    }
}


