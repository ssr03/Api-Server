package framework.apiserver.core.push.exception;

import framework.apiserver.core.error.exception.BusinessException;
import framework.apiserver.core.error.exception.ErrorCode;

public class PushTokenNotFoundException extends BusinessException {
    public PushTokenNotFoundException(){
        super(ErrorCode.TOKEN_NOT_FOUND);
    }
}
