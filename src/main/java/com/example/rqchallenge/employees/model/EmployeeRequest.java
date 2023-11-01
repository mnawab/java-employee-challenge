package com.example.rqchallenge.employees.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeRequest {
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;
    @NotNull
    @Min(0)
    @Max(200)
    private int age;
    @Min(0)
    private long salary;
    private String profileImage;
}
