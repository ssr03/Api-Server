package framework.apiserver.core.security.user.exception;

import framework.apiserver.core.error.exception.BusinessException;
import framework.apiserver.core.error.exception.ErrorCode;

public class UserException extends BusinessException {
    public UserException() {
        super(ErrorCode.USER_EXCEPTION);
    }

    public UserException(String message){
        super(message, ErrorCode.USER_EXCEPTION);
    }
}
