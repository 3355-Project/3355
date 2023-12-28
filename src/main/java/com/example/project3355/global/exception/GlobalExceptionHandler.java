package com.example.project3355.global.exception;

import com.example.project3355.global.common.CommonResponseDto;
import com.example.project3355.global.exception.columns.ApiException;
import com.example.project3355.global.exception.columns.ErrorResponse;
import com.example.project3355.global.exception.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
    return ResponseEntity.badRequest()
        .body(new CommonResponseDto(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage(),
                401));
  }


  @ExceptionHandler(ApiException.class)
  protected ResponseEntity<Object> handleCustomException(ApiException ex) {
    ErrorCode errorCode = ex.getErrorCode();
    String message = ex.getMessage();
    if (!StringUtils.hasText(message)) {
      message = errorCode.getMessage();
    }
    log.error("handleCustomException throw CustomException : {}", errorCode);
    return ResponseEntity.status(errorCode.getStatus()).body(
        new ErrorResponse(errorCode.getStatus(), message)
    );
  }
}