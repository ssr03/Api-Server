package framework.apiserver.core.file.Exception;

import framework.apiserver.core.error.exception.EntityNotFoundException;

public class FileNotFoundException extends EntityNotFoundException {
    public FileNotFoundException(String message){
        super(message);
    }
}
