package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.model.EmployeeRequest;
import com.example.rqchallenge.employees.model.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

@Service
public class EmployeeService {

    private final DummyService dummyService;

    @Autowired
    public EmployeeService(DummyService dummyService) {
        this.dummyService = dummyService;
    }

    public List<Employee> getAllEmployees() {
        return dummyService.getAllEmployees().getData();
    }

    public Optional<Employee> getEmployeeById(String id) {
        return dummyService.getEmployeeById(id).map(EmployeeResponse::getData);
    }

    public boolean deleteEmployeeById(String id) {
        return dummyService.deleteEmployeeById(id);
    }

    public Employee createEmployee(EmployeeRequest employeeRequest) {
        return dummyService.createEmployee(employeeRequest).getData();
    }

    public List<Employee> getEmployeesByNameSearch(String searchString) {
        return getAllEmployees().stream().filter(employee -> searchString.equals(employee.getName())).collect(toList());
    }

    public Integer getHighestSalaryOfEmployees() {
        Optional<Employee> maxSalaryEmp = getAllEmployees().stream().max(Comparator.comparing(Employee::getSalary));
        return Math.toIntExact(maxSalaryEmp.get().getSalary());
    }

    public List<String> getTopTenHighestEarningEmployeeNames() {
        return getAllEmployees().stream().
                sorted(Comparator.comparingLong(Employee::getSalary).reversed()).
                limit(10).map(Employee::getName).collect(toList());
    }
}
