package com.example.project3355.global.exception.jwt;

import com.example.project3355.global.exception.common.BusinessException;
import com.example.project3355.global.exception.common.ErrorCode;

public class InvalidJwtTokenException extends BusinessException {

  public InvalidJwtTokenException(Throwable cause) {
    super(ErrorCode.INVALID_JWT_TOKEN_EXCEPTION, cause);
  }
}
