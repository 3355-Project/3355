package com.example.project3355.global.exception.user;

import com.example.project3355.global.exception.common.BusinessException;
import com.example.project3355.global.exception.common.ErrorCode;

public class AlreadyExistEmailException extends BusinessException {

  public AlreadyExistEmailException() {
    super(ErrorCode.ALREADY_EXIST_EMAIL_EXCEPTION);
  }
}
