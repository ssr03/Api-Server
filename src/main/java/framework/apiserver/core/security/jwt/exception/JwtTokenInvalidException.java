package framework.apiserver.core.security.jwt.exception;

import framework.apiserver.core.error.exception.BusinessException;
import framework.apiserver.core.error.exception.ErrorCode;

public class JwtTokenInvalidException extends BusinessException {
    public JwtTokenInvalidException() {
        super(ErrorCode.REFRESH_TOKEN_INVALID);
    }
}
