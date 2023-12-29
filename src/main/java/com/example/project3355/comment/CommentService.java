package com.example.project3355.comment;

import com.example.project3355.user.entity.User;

public interface CommentService {

  CommentResponseDto createComment(Long id,CommentRequestDto requestDto, User user);

  CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, User user);

  void deleteComment(Long id, User user);
}
