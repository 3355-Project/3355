package com.example.project3355.global.exception.user;

import com.example.project3355.global.exception.common.BusinessException;
import com.example.project3355.global.exception.common.ErrorCode;

public class AlreadyExistUsernameException extends BusinessException {

  public AlreadyExistUsernameException() {
    super(ErrorCode.ALREADY_EXIST_USERNAME_EXCEPTION);
  }
}
