package com.example.project3355.global.exception.jwt;

import com.example.project3355.global.exception.common.BusinessException;
import com.example.project3355.global.exception.common.ErrorCode;

public class ExpiredJwtTokenException extends BusinessException {

  public ExpiredJwtTokenException(Throwable cause) {
    super(ErrorCode.EXPIRED_JWT_TOKEN_EXCEPTION, cause);
  }
}
