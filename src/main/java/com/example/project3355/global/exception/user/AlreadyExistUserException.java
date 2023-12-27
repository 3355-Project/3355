package com.example.project3355.global.exception.user;

import com.example.project3355.global.exception.common.BusinessException;
import com.example.project3355.global.exception.common.ErrorCode;

public class AlreadyExistUserException extends BusinessException {

  public AlreadyExistUserException() {
    super(ErrorCode.ALREADY_EXIST_MEMBER_EXCEPTION);
  }
}
