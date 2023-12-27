package com.example.project3355.global.exception.columns;




import com.example.project3355.global.exception.common.ErrorCode;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
@Generated
@Getter
public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(ErrorCode errorCode){
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.status = httpStatus.value();
        this.message = message;
    }


}
