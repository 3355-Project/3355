package com.example.project3355.coulmn;


import com.example.project3355.global.exception.columns.ApiException;
import com.example.project3355.global.exception.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ColumnsServiceImpl implements ColumnsService{
  private final ColumnsRepository columnsRepository;
  //보드레포 추가

  @Override
  public ColumnsResponseDto createColumns(ColumnsRequestDto columnsRequestDto, Long boardId) {

    //보드 아이디로 보드 존재 확인
//    ColumnsSequenceDto sequenceDto = new ColumnsSequenceDto();
//    sequenceDto.setSequence(columnsRepository.countByBoardId(boardId).intValue());
    Columns columns = new Columns(columnsRequestDto);
//    columns.addSequence(sequenceDto);
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

  @Override
  public void sequenceColumns(Long id, Long sequenceId) {
    Columns columns = findId(id);
    //아직 완료 x
  }


  public Columns findId(Long id){
    Columns columns = columnsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("일치하는 컬럼이 없어요"));
    return columns;
  }


}
