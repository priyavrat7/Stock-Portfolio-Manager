package com.mcgill.application.service;

import com.mcgill.application.model.Person;
import com.mcgill.application.repository.EmployeeRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * EmployeeService - Business Logic Layer
 * Handles all business rules and validation for employees
 */
public class EmployeeService {
    
    private EmployeeRepository repository;
    private ObservableList<Person> employees;
    
    public EmployeeService() {
        repository = new EmployeeRepository();
        employees = FXCollections.observableArrayList();
        loadEmployees();
    }
    
    /**
     * Add a new employee (Business Logic)
     */
    public String addEmployee(Person employee) {
        // Business Rule 1: All fields except remarks are required
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            return "Name is required!";
        }
        if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) {
            return "Email is required!";
        }
        if (employee.getAge() < 0) {
            return "Age cannot be negative!";
        }
        
        // Business Rule 2: Email must contain @
        if (!employee.getEmail().contains("@")) {
            return "Please enter a valid email address!";
        }
        
        // Business Rule 3: No duplicate IDs
        if (repository.existsById(employee.getId())) {
            return "Employee with this ID already exists!";
        }
        
        // All validations passed - save
        repository.save(employee);
        employees.add(employee);
        return null; // Success
    }
    
    /**
     * Delete an employee
     */
    public String deleteEmployee(Person employee) {
        if (employee == null) {
            return "Please select an employee to delete!";
        }
        
        repository.delete(employee.getId());
        employees.remove(employee);
        return null; // Success
    }
    
    /**
     * Get all employees
     */
    public ObservableList<Person> getAllEmployees() {
        return employees;
    }
    
    /**
     * Validate employee ID for duplication
     */
    public boolean isDuplicateId(int id) {
        return repository.existsById(id);
    }
    
    /**
     * Load all employees from repository
     */
    private void loadEmployees() {
        employees.clear();
        employees.addAll(repository.findAll());
    }
}

