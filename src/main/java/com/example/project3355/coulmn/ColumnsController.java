package com.example.project3355.coulmn;




import static com.example.project3355.global.exception.columns.ResponseCode.SUCCESS_COLUMNS;
import static com.example.project3355.global.exception.columns.ResponseCode.SUCCESS_COLUMNS_DELETE;
import static com.example.project3355.global.exception.columns.ResponseCode.SUCCESS_COLUMNS_SEQUENCE;
import static com.example.project3355.global.exception.columns.ResponseCode.SUCCESS_COLUMNS_UPDATE;

import com.example.project3355.global.exception.columns.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/boards")
public class ColumnsController {

  private final ColumnsService columnsService;


  @PostMapping("/{boardId}/columns")
  public ResponseEntity<SuccessResponse> createColumns(
      @RequestBody ColumnsRequestDto columnsRequestDto,
      @PathVariable Long boardId){
    ColumnsResponseDto responseDto = columnsService.createColumns(columnsRequestDto,boardId);

    return ResponseEntity.status(SUCCESS_COLUMNS.getHttpStatus()).body(new SuccessResponse(SUCCESS_COLUMNS,responseDto));

  }

  @PutMapping("/columns/{id}")
  public ResponseEntity<SuccessResponse> updateColumns(
      @RequestBody ColumnsRequestDto requestDto,
      @PathVariable Long id){
    ColumnsResponseDto responseDto = columnsService.updateColumns(requestDto,id);

    return ResponseEntity.status(SUCCESS_COLUMNS_UPDATE.getHttpStatus()).body(new SuccessResponse(SUCCESS_COLUMNS_UPDATE,responseDto));
  }

  @DeleteMapping("/columns/{id}")
  public ResponseEntity<SuccessResponse> deleteColumns(
      @PathVariable Long id){
    columnsService.deleteColumns(id);
    return ResponseEntity.status(SUCCESS_COLUMNS_DELETE.getHttpStatus()).body(new SuccessResponse(SUCCESS_COLUMNS_DELETE));
  }

  @PutMapping("/{boardId}/columns/{id}/{sequence}")
  public ResponseEntity<SuccessResponse> sequenceColumns(@PathVariable Long boardId,@PathVariable Long id,@PathVariable Integer sequence){
    columnsService.sequenceColumns(boardId,id,sequence);
    return ResponseEntity.status(SUCCESS_COLUMNS_SEQUENCE.getHttpStatus()).body(new SuccessResponse(SUCCESS_COLUMNS_SEQUENCE));
  }



}
