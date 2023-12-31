package com.example.project3355.global.exception.card;

import com.example.project3355.global.exception.common.BusinessException;
import com.example.project3355.global.exception.common.ErrorCode;

public class CardAssigneeAuthorizationException extends BusinessException {

  public CardAssigneeAuthorizationException() {
    super(ErrorCode.CARD_ASSIGNEE_AUTHORIZATION_EXCEPTION);
  }
}
