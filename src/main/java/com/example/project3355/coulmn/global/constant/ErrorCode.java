package com.example.project3355.coulmn.global.constant;

import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@Generated
public enum ErrorCode {

    INVALID_VALUE(HttpStatus.BAD_REQUEST,"잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러"),


    INVALID_PASSWROD_NICKNAME(HttpStatus.BAD_REQUEST,"패스워드에 nickname과 같은값이 있습니다."),
    INVALID_PASSWORD_CONFIRM(HttpStatus.BAD_REQUEST,"패스워드 확인이 패스워드랑 같지 않습니다."),
    INVALID_COLUMNS(HttpStatus.NOT_FOUND,"일치하는 컬럼이 없어요");



    private final HttpStatus httpStatus;
    private final String message;


}
