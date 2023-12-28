package com.example.project3355.global.exception.user;

import com.example.project3355.global.exception.common.BusinessException;
import com.example.project3355.global.exception.common.ErrorCode;

public class PasswordConfirmationException extends BusinessException {

  public PasswordConfirmationException() {
    super(ErrorCode.PASSWORD_CONFIRMATION_EXCEPTION);
  }
}
