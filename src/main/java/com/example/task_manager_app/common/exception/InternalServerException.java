package com.example.task_manager_app.common.exception;

public class InternalServerException extends Throwable {

    public InternalServerException(String message) {
        super(message);
    }


    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }


    public InternalServerException(Throwable cause) {
        super(cause);
    }
    
}
