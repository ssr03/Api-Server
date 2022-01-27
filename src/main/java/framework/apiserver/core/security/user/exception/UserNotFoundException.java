package framework.apiserver.core.security.user.exception;

import framework.apiserver.core.error.exception.EntityNotFoundException;
import framework.apiserver.core.error.exception.ErrorCode;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }

    public UserNotFoundException(String target) {
        super(target, ErrorCode.USER_NOT_FOUND);
    }
}
