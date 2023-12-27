package com.example.project3355.global.exception.jwt;

import com.example.project3355.global.exception.common.BusinessException;
import com.example.project3355.global.exception.common.ErrorCode;

public class InvalidJwtSignatureException extends BusinessException {

  public InvalidJwtSignatureException(Throwable cause) {
    super(ErrorCode.INVALID_JWT_SIGNATURE_EXCEPTION, cause);
  }
}
