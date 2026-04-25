package com.example.task_manager_app.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.task_manager_app.common.response.ApiResponse;
import com.example.task_manager_app.common.response.ErrorResponse;

@Slf4j
@ControllerAdvice
public class GlobalException {
    private static final String BAD_REQUEST_MESSAGE = "Invalid data";
    private static final String INTERNAL_SERVER_MESSAGE = "Something went wrong";
    private static final String METHOD_NOT_SUPPORTED_MESSAGE = "HTTP method not supported";

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleUnauthorized(UnauthorizedException ex) {
        return build(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return build(HttpStatus.METHOD_NOT_ALLOWED, METHOD_NOT_SUPPORTED_MESSAGE);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBadRequest(BadRequestException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBadRequest(Exception ex) {
        return build(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE);
    }


    @ExceptionHandler({ForbiddenException.class, AccessDeniedException.class})
    public ResponseEntity<ApiResponse<ErrorResponse>> handleForbidden(Exception ex) {
        return build(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler({NotFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ApiResponse<ErrorResponse>> handleNotFound(Exception ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleAll(Exception ex) {
        log.error("Unhandled exception occurred", ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_MESSAGE);
    }

    private ResponseEntity<ApiResponse<ErrorResponse>> build(HttpStatus status, String message) {
        ErrorResponse err = new ErrorResponse(message, status.value(), System.currentTimeMillis());
        ApiResponse<ErrorResponse> res = new ApiResponse<>(null, err);
        return new ResponseEntity<>(res, status);
    }
}
