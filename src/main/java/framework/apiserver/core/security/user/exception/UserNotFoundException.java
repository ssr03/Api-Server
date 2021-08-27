package framework.apiserver.core.security.user.exception;

import framework.apiserver.core.security.user.exception.UserException;

public class UserNotFoundException extends UserException {

    private static final long serialVersionUID = -6641166745915213326L;

    UserNotFoundException(String msg) {
        super(msg);
    }

    public UserNotFoundException() {
        super("사용자 목록이 없습니다.");
    }
}
