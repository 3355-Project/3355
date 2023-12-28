package com.example.project3355.coulmn;

public interface ColumnsService {

  ColumnsResponseDto createColumns(ColumnsRequestDto columnsRequestDto, Long boardId);

  ColumnsResponseDto updateColumns(ColumnsRequestDto requestDto, Long id);

  void deleteColumns(Long id);

  void sequenceColumns(Long boardId,Long id, Integer sequence);
}
