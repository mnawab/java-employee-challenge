package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.model.EmployeeRequest;
import com.example.rqchallenge.employees.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.MediaType.*;
import static java.lang.String.*;

@RestController
@RequestMapping("v1")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/employees", produces = APPLICATION_JSON)
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@NotNull @PathVariable String id) {
        Optional<Employee> employeeOptional = employeeService.getEmployeeById(id);
        return employeeOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@NotNull @PathVariable String id){
        boolean isDeleted = employeeService.deleteEmployeeById(id);

        if (isDeleted){
            return ResponseEntity.ok(format("Employee with id %s successfully deleted", id));
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest){
        return ResponseEntity.ok(employeeService.createEmployee(employeeRequest));
    }

    @GetMapping("/search/{searchString}")
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@NotNull @PathVariable String searchString){
        return ResponseEntity.ok(employeeService.getEmployeesByNameSearch(searchString));
    }

    @GetMapping("/highestSalary")
    ResponseEntity<Integer> getHighestSalaryOfEmployees(){
        return ResponseEntity.ok(employeeService.getHighestSalaryOfEmployees());
    }

    @GetMapping("/topTenHighestEarningEmployeeNames")
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames(){
        return ResponseEntity.ok(employeeService.getTopTenHighestEarningEmployeeNames());
    }

}
