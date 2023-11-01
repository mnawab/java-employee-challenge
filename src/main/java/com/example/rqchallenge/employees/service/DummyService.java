package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.config.DummyServiceConfig;
import com.example.rqchallenge.employees.exception.DummyServiceCallException;
import com.example.rqchallenge.employees.exception.InvalidResponseException;
import com.example.rqchallenge.employees.model.EmployeeRequest;
import com.example.rqchallenge.employees.model.EmployeeResponse;
import com.example.rqchallenge.employees.model.EmployeesResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static java.lang.String.*;

@Service
public class DummyService {
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private final Client client;
    private final DummyServiceConfig dummyServiceConfig;

    @Autowired
    public DummyService(Client client, DummyServiceConfig dummyServiceConfig) {
        this.client = client;
        this.dummyServiceConfig = dummyServiceConfig;
    }

    public EmployeesResponse getAllEmployees() {
        Response response;
        try {
            response = client.target(dummyServiceConfig.getGetEmployeesUrl()).request(APPLICATION_JSON_TYPE).header(CONTENT_TYPE_KEY, APPLICATION_JSON_TYPE).get();
        } catch (Exception ex) {
            throw new DummyServiceCallException(ex.getMessage());
        }

        if (response.getStatus() == SC_OK) {
            return response.readEntity(new GenericType<>() {
            });
        }

        throw new InvalidResponseException(format("Failed to retrieve getAllEmployees with [Response Code: %s]", response.getStatus()));

    }

    public Optional<EmployeeResponse> getEmployeeById(String id) {
        Response response;
        try {
            response = client.target(format(dummyServiceConfig.getGetEmployeeUrl(), id)).request(APPLICATION_JSON_TYPE).header(CONTENT_TYPE_KEY, APPLICATION_JSON_TYPE).get();
        } catch (Exception ex) {
            throw new DummyServiceCallException(ex.getMessage());
        }

        if (response.getStatus() == SC_NOT_FOUND) {
            return Optional.empty();
        }

        if (response.getStatus() == SC_OK) {
            return Optional.of(response.readEntity(new GenericType<>() {
            }));
        }

        throw new InvalidResponseException(format("Failed to retrieve getEmployeeById for [Id: %s ] with [Response Code: %s]", id, response.getStatus()));

    }

    public boolean deleteEmployeeById(String id) {
        Response response;
        try {
            response = client.target(format(dummyServiceConfig.getDeleteEmployeeUrl(), id)).request(APPLICATION_JSON_TYPE).header(CONTENT_TYPE_KEY, APPLICATION_JSON_TYPE).delete();
        } catch (Exception ex) {
            throw new DummyServiceCallException(ex.getMessage());
        }

        if (response.getStatus() == SC_NOT_FOUND) {
            return false;
        }

        if (response.getStatus() == SC_OK) {
            return true;
        }

        throw new InvalidResponseException(format("Failed to deleteEmployeeById for [Id: %s ] with [Response Code: %s]", id, response.getStatus()));

    }

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        Response response;
        try {
            response = client.target(dummyServiceConfig.getCreateEmployeeUrl()).request(APPLICATION_JSON_TYPE).header(CONTENT_TYPE_KEY, APPLICATION_JSON_TYPE).post(Entity.json(employeeRequest));
        } catch (Exception ex) {
            throw new DummyServiceCallException(ex.getMessage());
        }

        if (response.getStatus() == SC_OK) {
            return response.readEntity(new GenericType<>() {
            });
        }

        throw new InvalidResponseException(format("Failed to createEmployee with [Response Code: %s]", response.getStatus()));

    }
}
