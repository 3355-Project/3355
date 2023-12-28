package com.example.project3355.coulmn;


import com.example.project3355.board.entity.Board;
import com.example.project3355.board.repository.BoardRepository;
import com.example.project3355.global.exception.columns.ApiException;
import com.example.project3355.global.exception.common.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class ColumnsServiceImpl implements ColumnsService{
  private final ColumnsRepository columnsRepository;
  private final BoardRepository boardRepository;

  @Override
  public ColumnsResponseDto createColumns(ColumnsRequestDto columnsRequestDto, Long boardId) {

    Board board = boardRepository.findById(boardId).orElseThrow(()-> new ApiException(ErrorCode.INVALID_BOARD));
    ColumnsSequenceDto sequenceDto = new ColumnsSequenceDto(columnsRepository.countByBoardId(boardId).intValue()+1);
    Columns columns = new Columns(columnsRequestDto,board);
    columns.addSequence(sequenceDto);
    Columns saveColumns = columnsRepository.save(columns);
    return new ColumnsResponseDto(saveColumns);
  }

  @Transactional
  @Override
  public ColumnsResponseDto updateColumns(ColumnsRequestDto requestDto, Long id) {
    Columns columns = findId(id);
    columns.update(requestDto);
    return new ColumnsResponseDto(columns);
  }

  @Override
  public void deleteColumns(Long id) {
    Columns columns = findId(id);
    columnsRepository.delete(columns);
  }

  @Transactional
  @Override
  public void sequenceColumns(Long boardId,Long id, Integer sequence) {
    Columns columns = findId(id);
    if(columns.getSequence().equals(sequence)){
      throw new ApiException(ErrorCode.INVALID_COLUMNS_SEQUENCE);
    }
    else {
      List<Columns> columnsList = columnsRepository.findByBoardIdAndSequenceBetween(boardId,columns.getSequence(),sequence);   // columns.getSequence()가 더낮다는 전체하에

//      for(Columns column : columnsList){
//        if(column.getSequence().equals(columns.getSequence())){
//          column.addSequence();
//        }
//      }

    }

  }


  public Columns findId(Long id){
    Columns columns = columnsRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.INVALID_COLUMNS));
    return columns;
  }


}
