package framework.apiserver.core.security.jwt.exception;

import framework.apiserver.core.error.exception.BusinessException;
import framework.apiserver.core.error.exception.ErrorCode;

public class JwtTokenNotFoundException extends BusinessException {
    public JwtTokenNotFoundException(){
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
