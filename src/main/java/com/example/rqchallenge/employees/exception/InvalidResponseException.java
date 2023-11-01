package com.example.rqchallenge.employees.exception;

import jakarta.ws.rs.WebApplicationException;

public class InvalidResponseException extends WebApplicationException {
    public InvalidResponseException(String message) {
        super(message);
    }
}
