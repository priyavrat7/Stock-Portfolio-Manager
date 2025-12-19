package com.mcgill.application.repository;

import com.mcgill.application.model.Person;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EmployeeRepository - Data Access Layer
 * Currently uses in-memory storage
 * Ready to be upgraded to PostgreSQL later
 */
public class EmployeeRepository {
    
    // In-memory storage (will be replaced with database)
    private final Map<Integer, Person> employeeStorage;
    
    public EmployeeRepository() {
        employeeStorage = new HashMap<>();
        // Initialize with sample data
        initializeSampleData();
    }
    
    /**
     * Save an employee
     * In future: INSERT INTO employees table
     */
    public void save(Person employee) {
        employeeStorage.put(employee.getId(), employee);
    }
    
    /**
     * Find all employees
     * In future: SELECT * FROM employees
     */
    public List<Person> findAll() {
        return new ArrayList<>(employeeStorage.values());
    }
    
    /**
     * Find employee by ID
     * In future: SELECT * FROM employees WHERE id = ?
     */
    public Person findById(int id) {
        return employeeStorage.get(id);
    }
    
    /**
     * Check if employee with ID exists
     * In future: SELECT COUNT(*) FROM employees WHERE id = ?
     */
    public boolean existsById(int id) {
        return employeeStorage.containsKey(id);
    }
    
    /**
     * Delete employee by ID
     * In future: DELETE FROM employees WHERE id = ?
     */
    public void delete(int id) {
        employeeStorage.remove(id);
    }
    
    /**
     * Update employee
     * In future: UPDATE employees SET ... WHERE id = ?
     */
    public void update(Person employee) {
        employeeStorage.put(employee.getId(), employee);
    }
    
    /**
     * Initialize with sample data
     */
    private void initializeSampleData() {
        save(new Person(1, "John Doe", "john@mcgill.ca", 28, "Senior Developer"));
        save(new Person(2, "Jane Smith", "jane@mcgill.ca", 32, "Team Lead"));
        save(new Person(3, "Bob Johnson", "bob@mcgill.ca", 45, "Manager"));
        save(new Person(4, "Alice Williams", "alice.williams@mcgill.ca", 26, "Junior Developer"));
        save(new Person(5, "Michael Brown", "michael.brown@mcgill.ca", 51, "Director"));
        save(new Person(6, "Emily Davis", "emily.davis@mcgill.ca", 23, "Intern"));
        save(new Person(7, "Chris Lee", "chris.lee@mcgill.ca", 37, "Senior Developer"));
        save(new Person(8, "Jessica Taylor", "jessica.taylor@mcgill.ca", 29, "Product Manager"));
        save(new Person(9, "David Anderson", "david.anderson@mcgill.ca", 41, "Architect"));
        save(new Person(10, "Sarah Martinez", "sarah.martinez@mcgill.ca", 33, "Tech Lead"));
        save(new Person(11, "Daniel Wang", "daniel.wang@mcgill.ca", 27, "Developer"));
        save(new Person(12, "Sophia Clark", "sophia.clark@mcgill.ca", 24, "Junior Developer"));
        save(new Person(13, "Matthew Lewis", "matthew.lewis@mcgill.ca", 40, "Senior Engineer"));
    }
}

