package framework.apiserver.core.push.exception;


import framework.apiserver.core.error.exception.BusinessException;
import framework.apiserver.core.error.exception.ErrorCode;

public class PushFcmException extends BusinessException {
    public PushFcmException(){
        super(ErrorCode.FCM_INVALID);
    }
}
