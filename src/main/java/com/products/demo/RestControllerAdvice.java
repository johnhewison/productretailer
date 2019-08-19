package com.products.demo;

import com.products.demo.api.ExceptionApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(ProductBlockedException.class)
    protected ResponseEntity<ExceptionApi> handleProductBlockedException(ProductBlockedException ex, WebRequest request) {
        log.error("ProductBlockedException.", ex);
        return handleException(ex.getClass(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    protected ResponseEntity<ExceptionApi> handleProductNotFoundException(ProductNotFoundException ex, WebRequest request) {
        log.error("ProductNotFoundException.", ex);
        return handleException(ex.getClass(), ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ResponseEntity<ExceptionApi> handleRuntimeException(RuntimeException ex, WebRequest request) {
        log.error("RuntimeException.", ex);
        return handleException(ex.getClass(), ex.getMessage(), INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionApi> handleException(Class exception, String message, HttpStatus httpStatus) {
        ExceptionApi exceptionApi = new ExceptionApi(exception.getSimpleName(), message);
        return new ResponseEntity<>(exceptionApi, httpStatus);
    }
}
