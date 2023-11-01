package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.TestHelper;
import com.example.rqchallenge.employees.config.DummyServiceConfig;
import com.example.rqchallenge.employees.exception.InvalidResponseException;
import com.example.rqchallenge.employees.model.EmployeeResponse;
import com.example.rqchallenge.employees.model.EmployeesResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.*;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class DummyServiceUnitTest {
    private static final String GET_EMPLOYEES_URL = "http://localhost:8080/api/v1/employees";
    private static final String GET_EMPLOYEE_URL = "http://localhost:8080/api/v1/employee/%s";
    private static final String DELETE_EMPLOYEE_URL = "http://localhost:8080/api/v1/delete/%s";
    private static final String CREATE_EMPLOYEE_URL = "http://localhost:8080/api/v1/create";
    @Mock
    private Client client;

    @Mock
    private WebTarget webTarget;

    @Mock
    private Invocation.Builder builder;

    @Mock
    private Response response;

    @Mock
    private DummyServiceConfig dummyServiceConfig;

    private DummyService dummyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(dummyServiceConfig.getGetEmployeesUrl()).thenReturn(GET_EMPLOYEES_URL);
        when(dummyServiceConfig.getGetEmployeeUrl()).thenReturn(GET_EMPLOYEE_URL);
        when(dummyServiceConfig.getDeleteEmployeeUrl()).thenReturn(DELETE_EMPLOYEE_URL);
        when(dummyServiceConfig.getCreateEmployeeUrl()).thenReturn(CREATE_EMPLOYEE_URL);
        when(client.target(anyString())).thenReturn(webTarget);
        when(webTarget.request(APPLICATION_JSON_TYPE)).thenReturn(builder);
        when(builder.header("Content-Type", APPLICATION_JSON_TYPE)).thenReturn(builder);
        dummyService = new DummyService(client, dummyServiceConfig);
    }

    @Test
    void getAllEmployeesShouldReturn200() {
        when(builder.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(SC_OK);
        when(response.readEntity(any(GenericType.class))).thenReturn(TestHelper.employeesResponse());

        EmployeesResponse allEmployees = dummyService.getAllEmployees();

        assertEquals(11, allEmployees.getData().size());
    }

    @Test
    void getAllEmployeesShouldThrowInvalidResponseException() {
        when(builder.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(SC_INTERNAL_SERVER_ERROR);

        InvalidResponseException exception = assertThrows(InvalidResponseException.class, () -> dummyService.getAllEmployees());
        assertEquals("Failed to retrieve getAllEmployees with [Response Code: 500]", exception.getMessage());
    }

    @Test
    void getEmployeeByIdShouldReturnEmployee() {
        when(builder.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(SC_OK);
        when(response.readEntity(any(GenericType.class))).thenReturn(TestHelper.employeeResponse());

        Optional<EmployeeResponse> employeeByIdOptional = dummyService.getEmployeeById(TestHelper.TEST_ID);

        assertTrue(employeeByIdOptional.isPresent());
    }

    @Test
    void getEmployeeByIdShouldReturnEmptyOptional() {
        when(builder.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(SC_NOT_FOUND);

        Optional<EmployeeResponse> employeeByIdOptional = dummyService.getEmployeeById(TestHelper.TEST_ID);

        assertFalse(employeeByIdOptional.isPresent());
    }

    @Test
    void getEmployeeByIdShouldThrowInvalidResponseException() {
        when(builder.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(SC_BAD_GATEWAY);

        InvalidResponseException invalidResponseException = assertThrows(InvalidResponseException.class, () -> dummyService.getEmployeeById(TestHelper.TEST_ID));

        assertEquals("Failed to retrieve getEmployeeById for [Id: 1 ] with [Response Code: 502]", invalidResponseException.getMessage());
    }

    @Test
    void deleteEmployeeByIdDeleteSuccessfully() {
        when(builder.delete()).thenReturn(response);
        when(response.getStatus()).thenReturn(SC_OK);

        boolean isDeleted = dummyService.deleteEmployeeById(TestHelper.TEST_ID);

        assertTrue(isDeleted);
    }

    @Test
    void deleteEmployeeByIdShouldReturnFalseWhenRecordNotFound() {
        when(builder.delete()).thenReturn(response);
        when(response.getStatus()).thenReturn(SC_NOT_FOUND);

        boolean isDeleted = dummyService.deleteEmployeeById(TestHelper.TEST_ID);

        assertFalse(isDeleted);
    }

    @Test
    void deleteEmployeeByIdShouldThrowInvalidResponseException() {
        when(builder.delete()).thenReturn(response);
        when(response.getStatus()).thenReturn(SC_INTERNAL_SERVER_ERROR);

        InvalidResponseException invalidResponseException = assertThrows(InvalidResponseException.class, () -> dummyService.deleteEmployeeById(TestHelper.TEST_ID));
        assertEquals("Failed to deleteEmployeeById for [Id: 1 ] with [Response Code: 500]", invalidResponseException.getMessage());
    }

    @Test
    void createEmployeeShouldCreateEmployeeSuccessfully() {
        when(builder.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(SC_OK);
        when(response.readEntity(any(GenericType.class))).thenReturn(TestHelper.employeeResponse());

        EmployeeResponse employee = dummyService.createEmployee(TestHelper.employeeRequest());

        assertEquals(TestHelper.TEST_ID, employee.getData().getId());
    }

    @Test
    void createEmployeeShouldThrowInvalidResponseException() {
        when(builder.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(SC_INTERNAL_SERVER_ERROR);

        InvalidResponseException invalidResponseException = assertThrows(InvalidResponseException.class, () -> dummyService.createEmployee(TestHelper.employeeRequest()));

        assertEquals("Failed to createEmployee with [Response Code: 500]", invalidResponseException.getMessage());
    }
}