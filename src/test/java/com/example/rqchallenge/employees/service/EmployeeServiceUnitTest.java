package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.model.EmployeeRequest;
import com.example.rqchallenge.employees.model.EmployeeResponse;
import com.example.rqchallenge.employees.model.EmployeesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.example.rqchallenge.employees.TestHelper.*;
import static com.example.rqchallenge.employees.TestHelper.employeesResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EmployeeServiceUnitTest {
    @Mock
    private DummyService dummyService;

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeService(dummyService);
    }

    @Test
    void testGetAllEmployeesShouldReturnEmployees() {
        EmployeesResponse testEmployeesResponse = employeesResponse();
        when(dummyService.getAllEmployees()).thenReturn(testEmployeesResponse);
        EmployeesResponse allEmployees = dummyService.getAllEmployees();
        assertEquals(11, allEmployees.getData().size());
    }

    @Test
    void testGetEmployeeByIdShouldReturnEmployee() {
        when(dummyService.getEmployeeById(TEST_ID)).thenReturn(Optional.of(employeeResponse()));
        Optional<Employee> employeeOptional = employeeService.getEmployeeById(TEST_ID);
        assertTrue(employeeOptional.isPresent());
    }

    @Test
    void testDeleteEmployeeByIdSuccessfully() {
        when(dummyService.deleteEmployeeById(TEST_ID)).thenReturn(true);
        boolean isDeletedSuccessfully = employeeService.deleteEmployeeById(TEST_ID);
        assertTrue(isDeletedSuccessfully);
    }

    @Test
    void testDeleteEmployeeByIdReturnFalse() {
        when(dummyService.deleteEmployeeById(TEST_ID)).thenReturn(false);
        boolean isDeletedSuccessfully = employeeService.deleteEmployeeById(TEST_ID);
        assertFalse(isDeletedSuccessfully);
    }

    @Test
    void testCreateEmployeeSuccessfully() {
        EmployeeRequest employeeRequest = employeeRequest();
        EmployeeResponse employeeResponse = employeeResponse();
        when(dummyService.createEmployee(employeeRequest)).thenReturn(employeeResponse);
        Employee employee = employeeService.createEmployee(employeeRequest);
        assertEquals(employeeRequest.getName(), employee.getName());
        assertEquals(employeeRequest.getAge(), employee.getAge());
        assertEquals(employeeRequest.getSalary(), employee.getSalary());
        assertEquals(employeeRequest.getProfileImage(), employee.getProfileImage());
    }

    @Test
    void testGetEmployeesByNameSearchCorrectly() {
        when(dummyService.getAllEmployees()).thenReturn(employeesResponse());
        List<Employee> employeesByNameSearch = employeeService.getEmployeesByNameSearch(TEST_NAME);
        assertEquals(2, employeesByNameSearch.size());
        assertEquals(TEST_NAME, employeesByNameSearch.get(0).getName());
        assertEquals(TEST_NAME, employeesByNameSearch.get(1).getName());
    }

    @Test
    void testGetHighestSalaryOfEmployeesShouldReturnCorrectAmount() {
        when(dummyService.getAllEmployees()).thenReturn(employeesResponse());
        Integer highestSalaryOfEmployee = employeeService.getHighestSalaryOfEmployees();
        assertEquals(10000, highestSalaryOfEmployee);
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        when(dummyService.getAllEmployees()).thenReturn(employeesResponse());
        List<String> topTenHighestEarningEmployeeNames = employeeService.getTopTenHighestEarningEmployeeNames();
        assertEquals(10, topTenHighestEarningEmployeeNames.size());
        assertEquals("test-10", topTenHighestEarningEmployeeNames.get(0));
        assertEquals("test-9", topTenHighestEarningEmployeeNames.get(1));
        assertEquals("test-8", topTenHighestEarningEmployeeNames.get(2));
        assertEquals("test-7", topTenHighestEarningEmployeeNames.get(3));
        assertEquals("test-6", topTenHighestEarningEmployeeNames.get(4));
        assertEquals("test-5", topTenHighestEarningEmployeeNames.get(5));
        assertEquals("test-4", topTenHighestEarningEmployeeNames.get(6));
        assertEquals("test-3", topTenHighestEarningEmployeeNames.get(7));
        assertEquals("test-2", topTenHighestEarningEmployeeNames.get(8));
        assertEquals("test-1", topTenHighestEarningEmployeeNames.get(9));

    }
}