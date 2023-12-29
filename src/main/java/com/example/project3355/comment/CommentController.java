package com.example.project3355.comment;


import static com.example.project3355.global.exception.columns.ResponseCode.SUCCESS_COMMENT;
import static com.example.project3355.global.exception.columns.ResponseCode.SUCCESS_COMMENT_DELETE;
import static com.example.project3355.global.exception.columns.ResponseCode.SUCCESS_COMMENT_UPDATE;

import com.example.project3355.global.exception.columns.SuccessResponse;
import com.example.project3355.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cards")
public class CommentController {
  private final CommentService commentService;


  @PostMapping("/{cardId}/comment")
  public ResponseEntity<SuccessResponse> createComment(@PathVariable Long cardId,
      @RequestBody CommentRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails){
    CommentResponseDto responseDto = commentService.createComment(cardId,requestDto,userDetails.getUser());
    return ResponseEntity.status(SUCCESS_COMMENT.getHttpStatus()).body(new SuccessResponse(SUCCESS_COMMENT,responseDto));
  }

  @PutMapping("/{cardId}/comment/{id}")
  public ResponseEntity<SuccessResponse> updateComment(@PathVariable Long cardId,@PathVariable Long id,
      @RequestBody CommentRequestDto commentRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails){
    CommentResponseDto responseDto = commentService.updateComment(cardId,id,commentRequestDto,userDetails.getUser());
    return ResponseEntity.status(SUCCESS_COMMENT_UPDATE.getHttpStatus()).body(new SuccessResponse(SUCCESS_COMMENT_UPDATE,responseDto));
  }

  @DeleteMapping("/comment/{id}")
  public ResponseEntity<SuccessResponse> deleteComment(@PathVariable Long id,
      @AuthenticationPrincipal UserDetailsImpl userDetails){
    commentService.deleteComment(id,userDetails.getUser());
    return ResponseEntity.status(SUCCESS_COMMENT_DELETE.getHttpStatus()).body(new SuccessResponse(SUCCESS_COMMENT_DELETE));
  }



}
