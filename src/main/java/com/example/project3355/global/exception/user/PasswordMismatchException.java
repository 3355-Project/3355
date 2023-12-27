package com.example.project3355.global.exception.user;

import com.example.project3355.global.exception.common.BusinessException;
import com.example.project3355.global.exception.common.ErrorCode;

public class PasswordMismatchException extends BusinessException {

  public PasswordMismatchException() {
    super(ErrorCode.PASSWORD_MISMATCH_EXCEPTION);
  }
}
