package framework.apiserver.core.security.jwt.exception;

import framework.apiserver.core.error.exception.BusinessException;
import framework.apiserver.core.error.exception.ErrorCode;

public class JwtTokenIncorrectException extends BusinessException {
    public JwtTokenIncorrectException(){
        super(ErrorCode.TOKEN_INFO_INCORRECT);
    }
}
