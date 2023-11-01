package com.example.rqchallenge.employees.handler;

import com.example.rqchallenge.employees.exception.DummyServiceCallException;
import com.example.rqchallenge.employees.exception.InvalidResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(DummyServiceCallException.class)
    public ResponseEntity<?> handleDummyServiceCallException(DummyServiceCallException e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(InvalidResponseException.class)
    public ResponseEntity<?> handleInvalidResponseException(InvalidResponseException e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Default handler. Exception: {}: ", ExceptionUtils.getStackTrace(e));
        return ResponseEntity.internalServerError().build();
    }
}
