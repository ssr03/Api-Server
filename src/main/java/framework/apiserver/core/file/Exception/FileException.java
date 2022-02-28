package framework.apiserver.core.file.Exception;

import framework.apiserver.core.error.exception.BusinessException;
import framework.apiserver.core.error.exception.ErrorCode;

public class FileException extends BusinessException {

    public FileException(String message){
        super(message, ErrorCode.FILE_EXCEPTION);
    }
}
