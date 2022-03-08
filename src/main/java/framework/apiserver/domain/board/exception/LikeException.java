package framework.apiserver.domain.board.exception;

import framework.apiserver.core.error.exception.BusinessException;
import framework.apiserver.core.error.exception.ErrorCode;

public class LikeException extends BusinessException {
    public LikeException() {
        super(ErrorCode.LIKE_EXCEPTION);
    }

    public LikeException(String message){
        super(message, ErrorCode.LIKE_EXCEPTION);
    }
}
