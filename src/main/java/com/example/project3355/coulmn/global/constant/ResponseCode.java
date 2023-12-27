package com.example.project3355.coulmn.global.constant;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

	SUCCESS_COLUMNS(CREATED, "컬럼 생성 성공"),
	SUCCESS_COLUMNS_UPDATE(OK,"컬럼 수정 성공"),
	SUCCESS_COLUMNS_DELETE(OK,"컬럼 삭제 성공"),
	SUCCESS_COLUMNS_SEQUENCE(OK,"컬럼 순서 이동 성공");


	private final HttpStatus httpStatus;
	private final String message;
}
