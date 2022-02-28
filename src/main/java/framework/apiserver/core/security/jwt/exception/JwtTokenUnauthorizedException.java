package framework.apiserver.core.security.jwt.exception;


import framework.apiserver.core.error.exception.BusinessException;
import framework.apiserver.core.error.exception.ErrorCode;

public class JwtTokenUnauthorizedException extends BusinessException {
    public JwtTokenUnauthorizedException(){
        super(ErrorCode.TOKEN_UNAUTHORIZED);
    }
}
