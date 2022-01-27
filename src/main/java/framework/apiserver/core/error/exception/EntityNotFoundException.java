package framework.apiserver.core.error.exception;

public class EntityNotFoundException extends BusinessException{
    public EntityNotFoundException(){
        super(ErrorCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(String target){
        super(target, ErrorCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public EntityNotFoundException(String target, ErrorCode errorCode) {
        super(target, errorCode);
    }
}
