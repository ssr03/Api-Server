package framework.apiserver.core.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@JsonFormat
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "Invalid Input Value "),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "Method Not Allowed "),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "C003", "Entity Not Found "),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C004", "Server Error "),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C005", "Invalid Type Value "),
    HANDLE_ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "C006", "Access is Denied "),

    // FCM
    FCM_INVALID(HttpStatus.BAD_REQUEST, "M001", "FCM IS INVALID"),
    TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "M002","token, fcm push 전송 안됨"),
    TOKEN_INFO_INCORRECT(HttpStatus.BAD_REQUEST, "M003","token정보가 틀렸습니다"),

    //Security
    REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "S001","Refresh Token이 유효하지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "S002","로그아웃된 사용자입니다."),
    TOKEN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "S003","권한정보가 없는 토큰 입니다"),

    //File
    FILE_EXCEPTION(HttpStatus.BAD_REQUEST, "F001", "FILE EXCEPTION"),

    //USER
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자가 존재하지 않습니다"),
    USER_EXCEPTION(HttpStatus.BAD_REQUEST, "U002", "사용자 오류")
    ;

    private HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
