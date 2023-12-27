package com.example.project3355.global.exception;

import com.example.project3355.global.common.CommonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
    return ResponseEntity.badRequest()
        .body(new CommonResponseDto(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage(),
                401));
  }
}