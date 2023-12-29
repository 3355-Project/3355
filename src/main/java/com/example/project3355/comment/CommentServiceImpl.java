package com.example.project3355.comment;


import com.example.project3355.card.entity.Card;
import com.example.project3355.card.repository.CardRepository;
import com.example.project3355.coulmn.service.ColumnsService;
import com.example.project3355.global.exception.columns.ApiException;
import com.example.project3355.global.exception.common.ErrorCode;
import com.example.project3355.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl  implements CommentService {
  private final CommentRepository commentRepository;
  private final CardRepository cardRepository;
  private final ColumnsService columnsService;


  @Override
  public CommentResponseDto createComment(Long id,CommentRequestDto requestDto, User user) {
    Card card = findCardId(id);
//    columnsService.findMember();
    Comment comment = commentRepository.save(new Comment(requestDto,card,user));
    return new CommentResponseDto(comment);
  }

  @Transactional
  @Override
  public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto,
      User user) {
    Comment comment =findCommentId(id);
    comment.update(commentRequestDto);
    return new CommentResponseDto(comment);
  }

  @Override
  public void deleteComment(Long id, User user) {
    Comment comment =findCommentId(id);
    commentRepository.delete(comment);
  }

  public Card findCardId(Long id){
    Card card = cardRepository.findById(id).orElseThrow(()-> new ApiException(ErrorCode.INVALID_CARD));
    return card;
  }
  public Comment findCommentId(Long id){
    Comment comment =commentRepository.findById(id).orElseThrow(()-> new ApiException(ErrorCode.INVALID_COMMENT));
    return comment;
  }


}
