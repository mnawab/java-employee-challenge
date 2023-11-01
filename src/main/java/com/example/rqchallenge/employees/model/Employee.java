package com.example.rqchallenge.employees.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {
    private String id;
    private String name;
    private int age;
    private long salary;
    private String profileImage;
}
