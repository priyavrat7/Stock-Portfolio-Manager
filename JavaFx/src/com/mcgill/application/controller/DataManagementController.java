package com.mcgill.application.controller;

import com.mcgill.application.model.Person;
import com.mcgill.application.service.EmployeeService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * DataManagementController - Handles Employee Management UI and Events
 * MVC Controller Layer - Delegates business logic to EmployeeService
 */
public class DataManagementController {
    
    private Scene scene;
    private TableView<Person> table;
    private EmployeeService employeeService;
    
    // UI Components
    private TextField idField;
    private TextField nameField;
    private TextField emailField;
    private TextField ageField;
    private TextField remarksField;
    
    // Callback for navigation
    private Runnable onBackCallback;
    
    public DataManagementController(Runnable onBackCallback) {
        this.onBackCallback = onBackCallback;
        employeeService = new EmployeeService();
        createScene();
    }
    
    public Scene getScene() {
        return scene;
    }
    
    private void createScene() {
        // Title
        Label title = new Label("Data Management - Employee List");
        title.getStyleClass().add("mcgill-section-title");
        
        // Create table
        table = new TableView<>();
        
        // Columns
        TableColumn<Person, Number> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        idCol.setPrefWidth(80);
        
        TableColumn<Person, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nameCol.setPrefWidth(180);
        
        TableColumn<Person, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        emailCol.setPrefWidth(220);
        
        TableColumn<Person, Number> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(cellData -> cellData.getValue().ageProperty());
        ageCol.setPrefWidth(80);
        
        TableColumn<Person, String> remarksCol = new TableColumn<>("Remarks");
        remarksCol.setCellValueFactory(cellData -> cellData.getValue().remarksProperty());
        remarksCol.setPrefWidth(200);
        
        table.getColumns().addAll(idCol, nameCol, emailCol, ageCol, remarksCol);
        table.setItems(employeeService.getAllEmployees());
        
        // Input form
        Label formTitle = new Label("Add New Employee:");
        Label idLabel = new Label("ID:");
        idField = new TextField();
        idField.setPromptText("Enter ID");
        
        Label nameLabel = new Label("Name:");
        nameField = new TextField();
        nameField.setPromptText("Enter name");
        
        Label emailLabel = new Label("Email:");
        emailField = new TextField();
        emailField.setPromptText("Enter email");
        
        Label ageLabel = new Label("Age:");
        ageField = new TextField();
        ageField.setPromptText("Enter age");
        
        Label remarksLabel = new Label("Remarks:");
        remarksField = new TextField();
        remarksField.setPromptText("Enter remarks (optional)");
        
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.add(formTitle, 0, 0, 2, 1);
        formGrid.add(idLabel, 0, 1);
        formGrid.add(idField, 1, 1);
        formGrid.add(nameLabel, 0, 2);
        formGrid.add(nameField, 1, 2);
        formGrid.add(emailLabel, 0, 3);
        formGrid.add(emailField, 1, 3);
        formGrid.add(ageLabel, 0, 4);
        formGrid.add(ageField, 1, 4);
        formGrid.add(remarksLabel, 0, 5);
        formGrid.add(remarksField, 1, 5);
        
        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button addBtn = new Button("Add Employee");
        addBtn.getStyleClass().add("mcgill-button");
        addBtn.setOnAction(e -> handleAddEmployee());
        
        Button deleteBtn = new Button("Delete Selected");
        deleteBtn.getStyleClass().add("mcgill-button-delete");
        deleteBtn.setOnAction(e -> handleDeleteEmployee());
        
        buttonBox.getChildren().addAll(addBtn, deleteBtn);
        
        // Back button
        Button backBtn = new Button("← Back to Menu");
        backBtn.getStyleClass().add("mcgill-button-back");
        backBtn.setOnAction(e -> {
            if (onBackCallback != null) {
                onBackCallback.run();
            }
        });
        
        // Layout
        VBox container = new VBox(20, title, table, formGrid, buttonBox, backBtn);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(20));
        
        // Set table to grow
        VBox.setVgrow(table, Priority.ALWAYS);
        
        scene = new Scene(container);
        scene.getStylesheets().add(getClass().getResource("/mcgill-theme.css").toExternalForm());
    }
    
    /**
     * Handle Add Employee event
     * Controllers delegate to services for business logic
     */
    private void handleAddEmployee() {
        String idStr = idField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String ageStr = ageField.getText().trim();
        String remarks = remarksField.getText().trim();
        
        // Validate input
        if (idStr.isEmpty() || name.isEmpty() || email.isEmpty() || ageStr.isEmpty()) {
            showError("All fields are required!");
            return;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            int age = Integer.parseInt(ageStr);
            
            // Use remarks from input, default to empty
            if (remarks.isEmpty()) {
                remarks = "";
            }
            
            Person employee = new Person(id, name, email, age, remarks);
            
            // Delegate to service for business logic
            String errorMessage = employeeService.addEmployee(employee);
            
            if (errorMessage != null) {
                showError(errorMessage);
            } else {
                // Success - clear fields
                idField.clear();
                nameField.clear();
                emailField.clear();
                ageField.clear();
                remarksField.clear();
            }
        } catch (NumberFormatException ex) {
            showError("Please enter valid numbers for ID and Age!");
        }
    }
    
    /**
     * Handle Delete Employee event
     * Controllers delegate to services for business logic
     */
    private void handleDeleteEmployee() {
        Person selected = table.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showError("Please select an employee to delete!");
            return;
        }
        
        // Delegate to service
        String errorMessage = employeeService.deleteEmployee(selected);
        
        if (errorMessage != null) {
            showError(errorMessage);
        }
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

