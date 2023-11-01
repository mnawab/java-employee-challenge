package com.example.rqchallenge.employees;

import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.model.EmployeeRequest;
import com.example.rqchallenge.employees.model.EmployeeResponse;
import com.example.rqchallenge.employees.model.EmployeesResponse;

import java.util.List;

public class TestHelper {

    public static final String SUCCESS = "success";
    public static final String TEST_ID = "1";
    public static final String TEST_NAME = "test-1";

    public static EmployeesResponse employeesResponse(){
        return new EmployeesResponse(SUCCESS, employeeList());
    }

    public static List<Employee> employeeList() {
        return List.of(employee(),
                new Employee("2", "test-2", 30, 2000, ""),
                new Employee("3", "test-3", 40, 3000, ""),
                new Employee("4", "test-4", 40, 4000, ""),
                new Employee("5", "test-5", 40, 5000, ""),
                new Employee("6", "test-6", 40, 6000, ""),
                new Employee("7", "test-7", 40, 7000, ""),
                new Employee("8", "test-8", 40, 8000, ""),
                new Employee("9", "test-9", 40, 9000, ""),
                new Employee("10", "test-10", 40, 10000, ""),
                new Employee("11", "test-1", 40, 2000, "")
        );
    }

    public static EmployeeResponse employeeResponse(){
        return new EmployeeResponse(SUCCESS, employee());
    }

    public static Employee employee() {
        return new Employee("1", "test-1", 20, 1000, "");
    }

    public static EmployeeRequest employeeRequest(){
        return new EmployeeRequest("test-1", 20, 1000, "");
    }
}
