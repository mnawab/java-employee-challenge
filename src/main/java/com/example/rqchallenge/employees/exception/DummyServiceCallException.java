package com.example.rqchallenge.employees.exception;

import jakarta.ws.rs.WebApplicationException;

public class DummyServiceCallException extends WebApplicationException {
    public DummyServiceCallException(String message) {
        super(message);
    }
}
