package com.example.project3355.board.controller;

import com.example.project3355.board.service.BoardService;
import com.example.project3355.global.common.CommonResponseDto;
import com.example.project3355.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardInvitationController {

    private final BoardService boardService;

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
}


