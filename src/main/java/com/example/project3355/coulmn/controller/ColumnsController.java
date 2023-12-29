package com.example.project3355.coulmn.controller;




import static com.example.project3355.global.exception.columns.ResponseCode.SUCCESS_COLUMNS;
import static com.example.project3355.global.exception.columns.ResponseCode.SUCCESS_COLUMNS_DELETE;
import static com.example.project3355.global.exception.columns.ResponseCode.SUCCESS_COLUMNS_SEQUENCE;
import static com.example.project3355.global.exception.columns.ResponseCode.SUCCESS_COLUMNS_UPDATE;


import com.example.project3355.coulmn.dto.ColumnsRequestDto;
import com.example.project3355.coulmn.dto.ColumnsResponseDto;
import com.example.project3355.coulmn.service.ColumnsService;
import com.example.project3355.global.exception.columns.SuccessResponse;
import com.example.project3355.user.UserDetailsImpl;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ColumnsController {

  private final ColumnsService columnsService;


  @PostMapping("/{boardId}/columns")
  public ResponseEntity<SuccessResponse> createColumns(
      @PathVariable Long boardId,
      @RequestBody ColumnsRequestDto columnsRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails){

    ColumnsResponseDto responseDto = columnsService.createColumns(columnsRequestDto,boardId,userDetails.getUser());

    return ResponseEntity.status(SUCCESS_COLUMNS.getHttpStatus()).body(new SuccessResponse(SUCCESS_COLUMNS,responseDto));

  }

  //보드안에 있는 컬럼 전체 조회
  @GetMapping("/{id}/columns")
  ResponseEntity<List<ColumnsResponseDto>> getOneBoardColumns(@PathVariable Long id){
    return ResponseEntity.ok().body(columnsService.getOneBoardColumns(id));
  }

  @PutMapping("/columns/{id}")
  public ResponseEntity<SuccessResponse> updateColumns(
      @RequestBody ColumnsRequestDto requestDto,
      @PathVariable Long id,
      @AuthenticationPrincipal UserDetailsImpl userDetails){
    ColumnsResponseDto responseDto = columnsService.updateColumns(requestDto,id,userDetails.getUser());

    return ResponseEntity.status(SUCCESS_COLUMNS_UPDATE.getHttpStatus()).body(new SuccessResponse(SUCCESS_COLUMNS_UPDATE,responseDto));
  }

  @DeleteMapping("/columns/{id}")
  public ResponseEntity<SuccessResponse> deleteColumns(
      @PathVariable Long id,
      @AuthenticationPrincipal UserDetailsImpl userDetails){
    columnsService.deleteColumns(id,userDetails.getUser());
    return ResponseEntity.status(SUCCESS_COLUMNS_DELETE.getHttpStatus()).body(new SuccessResponse(SUCCESS_COLUMNS_DELETE));
  }

  @PutMapping("/columns/{id}/{sequence}")
  public ResponseEntity<SuccessResponse> sequenceColumns(
      @PathVariable Long id,@PathVariable Integer sequence,
      @AuthenticationPrincipal UserDetailsImpl userDetails){
    columnsService.sequenceColumns(id,sequence,userDetails.getUser());
    return ResponseEntity.status(SUCCESS_COLUMNS_SEQUENCE.getHttpStatus()).body(new SuccessResponse(SUCCESS_COLUMNS_SEQUENCE));
  }



}
