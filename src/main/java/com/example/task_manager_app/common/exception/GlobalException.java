package com.example.task_manager_app.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                System.currentTimeMillis());

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                System.currentTimeMillis());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                System.currentTimeMillis());

        return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN.value(),
                System.currentTimeMillis());

        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                System.currentTimeMillis());

        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
