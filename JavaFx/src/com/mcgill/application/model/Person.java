package com.mcgill.application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;

/**
 * Person Model Class with JavaFX Properties
 * Represents a simple data model for demonstration
 * Uses JavaFX properties for better integration with TableView
 */
public class Person {

    // JavaFX wrapper properties
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final IntegerProperty age = new SimpleIntegerProperty();
    private final StringProperty remarks = new SimpleStringProperty();
    
    public Person() {
        // Default constructor
    }
    
    public Person(int id, String name, String email, int age) {
        this.id.set(id);
        this.name.set(name);
        this.email.set(email);
        this.age.set(age);
        this.remarks.set(""); // Default empty remarks
    }
    
    public Person(int id, String name, String email, int age, String remarks) {
        this.id.set(id);
        this.name.set(name);
        this.email.set(email);
        this.age.set(age);
        this.remarks.set(remarks);
    }
    
    // ID property
    public int getId() {
        return id.get();
    }
    
    public void setId(int value) {
        id.set(value);
    }
    
    public IntegerProperty idProperty() {
        return id;
    }
    
    // Name property
    public String getName() {
        return name.get();
    }
    
    public void setName(String value) {
        name.set(value);
    }
    
    public StringProperty nameProperty() {
        return name;
    }
    
    // Email property
    public String getEmail() {
        return email.get();
    }
    
    public void setEmail(String value) {
        email.set(value);
    }
    
    public StringProperty emailProperty() {
        return email;
    }
    
    // Age property
    public int getAge() {
        return age.get();
    }
    
    public void setAge(int value) {
        age.set(value);
    }
    
    public IntegerProperty ageProperty() {
        return age;
    }
    
    // Remarks property
    public String getRemarks() {
        return remarks.get();
    }
    
    public void setRemarks(String value) {
        remarks.set(value);
    }
    
    public StringProperty remarksProperty() {
        return remarks;
    }
}

