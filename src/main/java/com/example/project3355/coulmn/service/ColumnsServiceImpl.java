package com.example.project3355.coulmn.service;


import com.example.project3355.board.entity.Board;
import com.example.project3355.board.entity.UserBoard;
import com.example.project3355.board.repository.BoardRepository;
import com.example.project3355.board.repository.UserBoardRepository;
import com.example.project3355.coulmn.dto.ColumnsRequestDto;
import com.example.project3355.coulmn.dto.ColumnsResponseDto;
import com.example.project3355.coulmn.dto.ColumnsSequenceDto;
import com.example.project3355.coulmn.entity.Columns;
import com.example.project3355.coulmn.repository.ColumnsRepository;
import com.example.project3355.global.exception.columns.ApiException;
import com.example.project3355.global.exception.common.ErrorCode;
import com.example.project3355.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class ColumnsServiceImpl implements ColumnsService{
  private final ColumnsRepository columnsRepository;
  private final BoardRepository boardRepository;
  private final UserBoardRepository userBoardRepository;

  @Override
  public ColumnsResponseDto createColumns(ColumnsRequestDto columnsRequestDto, Long boardId,
      User user) {
    Board board = boardRepository.findById(boardId).orElseThrow(()-> new ApiException(ErrorCode.INVALID_BOARD));


    List<UserBoard> list = findMember(boardId,user);
    if(list.isEmpty()){
      throw new ApiException(ErrorCode.INVALID_MEMBERS);
    }


    ColumnsSequenceDto sequenceDto = new ColumnsSequenceDto(columnsRepository.countByBoardId(boardId).intValue()+1);
    Columns columns = new Columns(columnsRequestDto,board);
    columns.addSequence(sequenceDto);
    Columns saveColumns = columnsRepository.save(columns);
    return new ColumnsResponseDto(saveColumns);
  }

  @Override
  public List<ColumnsResponseDto> getOneBoardColumns(Long id) {
    List<Columns> columnsList = columnsRepository.findAllByBoardIdOrderBySequence(id);
    List<ColumnsResponseDto> responseDtoList = new ArrayList<>();

    for(Columns columns : columnsList){
      responseDtoList.add(new ColumnsResponseDto(columns));
    }

    return responseDtoList;
  }

  @Transactional
  @Override
  public ColumnsResponseDto updateColumns(ColumnsRequestDto requestDto, Long id,User user) {
    Columns columns = findId(id);
    List<UserBoard> list = findMember(columns.getBoard().getId(),user);
    if(list.isEmpty()){
      throw new ApiException(ErrorCode.INVALID_MEMBERS);
    }

    columns.update(requestDto);
    return new ColumnsResponseDto(columns);
  }

  @Override
  public void deleteColumns(Long id,User user) {
    Columns columns = findId(id);
    List<UserBoard> list = findMember(columns.getBoard().getId(),user);
    if(list.isEmpty()){
      throw new ApiException(ErrorCode.INVALID_MEMBERS);
    }
    Long lastSequence = columnsRepository.countByBoardId(columns.getBoard().getId());

    List<Columns> columnsList = columnsRepository.findByBoardIdAndSequenceBetween(columns.getBoard().getId(),columns.getSequence(),lastSequence.intValue());
    for(Columns colum : columnsList){
      ColumnsSequenceDto sequenceDto = new ColumnsSequenceDto(colum.getSequence()-1);
      colum.addSequence(sequenceDto);
    }

    columnsRepository.delete(columns);
  }

  @Transactional
  @Override
  public void sequenceColumns(Long id, Integer sequence,User user) {
    Columns columns = findId(id);
    List<UserBoard> list = findMember(columns.getBoard().getId(),user);
    Long countSequence = columnsRepository.countByBoardId(columns.getBoard().getId());
    if(list.isEmpty()){
      throw new ApiException(ErrorCode.INVALID_MEMBERS);
    }
    if(countSequence.intValue()<sequence){
      throw new ApiException(ErrorCode.INVALID_SEQUENCE);
    }

    if(columns.getSequence().equals(sequence)){
      throw new ApiException(ErrorCode.INVALID_COLUMNS_SEQUENCE);
    }
    else {
      List<Columns> columnsList;
      if(columns.getSequence()<sequence){
        columnsList = columnsRepository.findByBoardIdAndSequenceBetween(columns.getBoard().getId(),columns.getSequence(),sequence); // columns.getSequence()가 더낮다는 전체하에
        for(Columns column : columnsList){
          if(Objects.equals(columns.getId(), column.getId())){
            ColumnsSequenceDto sequenceDto = new ColumnsSequenceDto(sequence);
            column.addSequence(sequenceDto);
          }
          else {
            ColumnsSequenceDto sequenceDto = new ColumnsSequenceDto(column.getSequence()-1);
            column.addSequence(sequenceDto);
          }
        }
      }
      else {
        columnsList = columnsRepository.findByBoardIdAndSequenceBetween(columns.getBoard().getId(),sequence,columns.getSequence());
        for(Columns column : columnsList){
          if(Objects.equals(columns.getId(), column.getId())){
            ColumnsSequenceDto sequenceDto = new ColumnsSequenceDto(sequence);
            column.addSequence(sequenceDto);
          }
          else {
            ColumnsSequenceDto sequenceDto = new ColumnsSequenceDto(column.getSequence()+1);
            column.addSequence(sequenceDto);
          }
        }
      }



    }

  }


  public Columns findId(Long id){
    Columns columns = columnsRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.INVALID_COLUMNS));
    return columns;
  }

  @Override
  public List<UserBoard> findMember(Long boardId,User user){
    List<UserBoard> list = userBoardRepository.findAllByBoardId(boardId);
    List<UserBoard> userBoardList = new ArrayList<>();
    for(UserBoard userBoard : list){
      if(userBoard.getUser().getUsername().equals(user.getUsername())){
        userBoardList.add(userBoard);
      }
    }

    return userBoardList;
  }




}
