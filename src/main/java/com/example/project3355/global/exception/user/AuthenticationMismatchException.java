package com.example.project3355.global.exception.user;

import com.example.project3355.global.exception.common.BusinessException;
import com.example.project3355.global.exception.common.ErrorCode;

public class AuthenticationMismatchException extends BusinessException {

  public AuthenticationMismatchException() {
    super(ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
  }
}
