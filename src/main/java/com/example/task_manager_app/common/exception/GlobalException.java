package com.example.task_manager_app.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.task_manager_app.common.response.ApiResponse;
import com.example.task_manager_app.common.response.ErrorResponse;

@ControllerAdvice
public class GlobalException {
    private static final String BAD_REQUEST_MESSAGE = "Invalid data";
    private static final String INTERNAL_SERVER_MESSAGE = "Something went wrong";

    @ExceptionHandler
    public ResponseEntity<ApiResponse<ErrorResponse>> handleNoHandlerFound(NoResourceFoundException ex) {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<ErrorResponse>> handleInvalidArgument(MethodArgumentNotValidException ex) {
        ErrorResponse err = new ErrorResponse(BAD_REQUEST_MESSAGE,
                HttpStatus.BAD_REQUEST.value(),
                System.currentTimeMillis());

        ApiResponse<ErrorResponse> res = new ApiResponse<>(null, err);
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<ErrorResponse>> handleNotFoundException(NotFoundException ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                System.currentTimeMillis());

        ApiResponse<ErrorResponse> res = new ApiResponse<>(null, err);
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBadRequestException(BadRequestException ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                System.currentTimeMillis());

        ApiResponse<ErrorResponse> res = new ApiResponse<>(null, err);
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<ErrorResponse>> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                System.currentTimeMillis());

        ApiResponse<ErrorResponse> res = new ApiResponse<>(null, err);
        return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<ErrorResponse>> handleForbiddenException(ForbiddenException ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN.value(),
                System.currentTimeMillis());

        ApiResponse<ErrorResponse> res = new ApiResponse<>(null, err);
        return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<ErrorResponse>> handleAllExceptions(Exception ex) {
        ErrorResponse err = new ErrorResponse(INTERNAL_SERVER_MESSAGE,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                System.currentTimeMillis());

        ApiResponse<ErrorResponse> res = new ApiResponse<>(null, err);
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
