package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.model.EmployeeRequest;
import com.example.rqchallenge.employees.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static com.example.rqchallenge.employees.TestHelper.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void retAllEmployeesShouldReturnEmployees() throws Exception {

        when(employeeService.getAllEmployees()).thenReturn(employeeList());

        mockMvc.perform(get("/v1/employees").contentType(APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        verify(employeeService).getAllEmployees();
    }

    @Test
    void getEmployeeByIdShouldReturnEmployee() throws Exception {

        when(employeeService.getEmployeeById(TEST_ID)).thenReturn(Optional.of(employee()));

        mockMvc.perform(get("/v1/1").contentType(APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        verify(employeeService).getEmployeeById(TEST_ID);
    }

    @Test
    void getEmployeeByIdShouldReturnNotFound() throws Exception {

        when(employeeService.getEmployeeById(TEST_ID)).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/1").contentType(APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();

        verify(employeeService).getEmployeeById(TEST_ID);
    }

    @Test
    void deleteEmployeeByIdShouldDeleteSuccessfully() throws Exception {
        when(employeeService.deleteEmployeeById(TEST_ID)).thenReturn(true);

        mockMvc.perform(delete("/v1/1").contentType(APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        verify(employeeService).deleteEmployeeById(TEST_ID);
    }

    @Test
    void deleteEmployeeByIdShouldReturnFalseWhenRecordDoesNotExist() throws Exception {
        when(employeeService.deleteEmployeeById(TEST_ID)).thenReturn(false);

        mockMvc.perform(delete("/v1/1").contentType(APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();

        verify(employeeService).deleteEmployeeById(TEST_ID);
    }

    @Test
    void createEmployeeSuccessfully() throws Exception {
        EmployeeRequest employeeRequest = employeeRequest();
        Employee employee = employee();
        when(employeeService.createEmployee(employeeRequest)).thenReturn(employee);

        mockMvc.perform(post("/v1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employeeRequest))).andExpect(status().isOk()).andReturn();
        verify(employeeService).createEmployee(employeeRequest);
    }

    @Test
    void getEmployeesByNameSearchShouldReturnSearchResult() throws Exception {
        when(employeeService.getEmployeesByNameSearch(TEST_NAME)).thenReturn(employeeList());

        mockMvc.perform(get("/v1/search/test-1").contentType(APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        verify(employeeService).getEmployeesByNameSearch(TEST_NAME);
    }

    @Test
    void getHighestSalaryOfEmployees() throws Exception {
        when(employeeService.getHighestSalaryOfEmployees()).thenReturn(1000);

        mockMvc.perform(get("/v1/highestSalary").contentType(APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        verify(employeeService).getHighestSalaryOfEmployees();
    }

    @Test
    void getTopTenHighestEarningEmployeeNames() throws Exception {
        when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(List.of("test-5", "test-2"));

        mockMvc.perform(get("/v1/topTenHighestEarningEmployeeNames").contentType(APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        verify(employeeService).getTopTenHighestEarningEmployeeNames();
    }
}