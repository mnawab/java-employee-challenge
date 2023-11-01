package com.example.rqchallenge.employees.handler;

import com.example.rqchallenge.employees.exception.DummyServiceCallException;
import com.example.rqchallenge.employees.exception.InvalidResponseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerUnitTest {

    private GlobalExceptionHandler globalExceptionHandler;
    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleDummyServiceCallException() {
        ResponseEntity<?> response = globalExceptionHandler.handleDummyServiceCallException(new DummyServiceCallException("test"));
        assertEquals(500, response.getStatusCode().value());
    }

    @Test
    void testHandleInvalidResponseException() {
        ResponseEntity<?> response = globalExceptionHandler.handleInvalidResponseException(new InvalidResponseException("test"));
        assertEquals(500, response.getStatusCode().value());
    }

    @Test
    void testHandleException() {
        ResponseEntity<?> response = globalExceptionHandler.handleException(new Exception("test"));
        assertEquals(500, response.getStatusCode().value());
    }

}