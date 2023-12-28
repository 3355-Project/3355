package com.example.project3355.coulmn.service;

import com.example.project3355.coulmn.dto.ColumnsRequestDto;
import com.example.project3355.coulmn.dto.ColumnsResponseDto;

public interface ColumnsService {

  ColumnsResponseDto createColumns(ColumnsRequestDto columnsRequestDto, Long boardId);

  ColumnsResponseDto updateColumns(ColumnsRequestDto requestDto, Long id);

  void deleteColumns(Long id);

  void sequenceColumns(Long boardId,Long id, Integer sequence);
}
