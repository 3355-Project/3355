package com.example.project3355.comment;


import com.example.project3355.card.repository.CardRepository;
import com.example.project3355.coulmn.service.ColumnsService;
import com.example.project3355.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl  implements CommentService {
  private final CommentRepository commentRepository;
  private final CardRepository cardRepository;
  private final ColumnsService columnsService;


  @Override
  public CommentResponseDto createComment(Long cardId, CommentRequestDto requestDto, User user) {
    return null;
  }

  @Override
  public CommentResponseDto updateComment(Long cardId, Long id, CommentRequestDto commentRequestDto,
      User user) {
    return null;
  }

  @Override
  public void deleteComment(Long id, User user) {

  }


}
