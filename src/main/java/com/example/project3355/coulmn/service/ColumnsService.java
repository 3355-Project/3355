package com.example.project3355.coulmn.service;

import com.example.project3355.board.entity.UserBoard;
import com.example.project3355.coulmn.dto.ColumnsRequestDto;
import com.example.project3355.coulmn.dto.ColumnsResponseDto;
import com.example.project3355.user.entity.User;
import java.util.List;

public interface ColumnsService {

  ColumnsResponseDto createColumns(ColumnsRequestDto columnsRequestDto, Long boardId, User user);

  List<ColumnsResponseDto> getOneBoardColumns(Long id);

  ColumnsResponseDto updateColumns(ColumnsRequestDto requestDto, Long id,User user);

  void deleteColumns(Long id,User user);

  void sequenceColumns(Long id, Integer sequence,User user);

  List<UserBoard> findMember(Long boardId,User user);


}
