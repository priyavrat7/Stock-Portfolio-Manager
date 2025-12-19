/*
 * Username: trader@mcgill.ca, Password: demo123
 * Username: admin@mcgill.ca, Password: admin123
 */

package com.mcgill.application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.mcgill.application.model.Stock;
import com.mcgill.application.controller.PortfolioController;
import com.mcgill.application.service.CalculatorService;

/**
 * McGill University Stock Market Platform
 * MVC Architecture - Application Entry Point
 * 
 * Architecture:
 * - View: UI Creation (JavaFX)
 * - Controller: Event handling and coordination
 * - Service: Business logic (Portfolio Management)
 * - Repository: Data access (Stock Holdings)
 * - Model: Stock data structures
 * 
 * Ready for PostgreSQL integration
 */
public class Main extends Application {
    
    private Stage primaryStage;
    private Scene loginScene;
    private Scene mainScene;
    private Scene calculatorScene;
    private Scene portfolioScene;
    
    // Calculator window (separate stage)
    private Stage calculatorStage;
    
    // User authentication state
    private boolean isLoggedIn = false;
    
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("McGill Stock Market Platform");
        
        // Create login scene first
        createLoginScene();
        
        // Load CSS
        loginScene.getStylesheets().addAll(
            getClass().getResource("/styles/theme.css").toExternalForm(),
            getClass().getResource("/styles/common.css").toExternalForm()
        );
        
        primaryStage.setScene(loginScene);
        primaryStage.setWidth(600);
        primaryStage.setHeight(450);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
    
    /**
     * Create Login Scene
     */
    private void createLoginScene() {
        // Title
        Label title = new Label("Portfolio");
        title.getStyleClass().add("mcgill-title");
        
        Label subtitle = new Label("Portfolio Management System");
        subtitle.getStyleClass().add("mcgill-subtitle");
        
        // Login Form
        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-font-weight: bold;");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setPrefWidth(300);
        
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-weight: bold;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefWidth(300);
        
        // Buttons
        Button loginBtn = new Button("Login");
        loginBtn.getStyleClass().add("mcgill-button");
        loginBtn.setPrefWidth(200);
        loginBtn.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            
            if (email.isEmpty() || password.isEmpty()) {
                showError("Please enter both email and password!");
                return;
            }
            
            // Simple authentication (can be enhanced later)
            if (authenticate(email, password)) {
                isLoggedIn = true;
                switchToMainMenu();
            } else {
                showError("Invalid email or password!");
            }
        });
        
        // Demo login button (for easy testing)
        Button demoLoginBtn = new Button("Use Demo Account");
        demoLoginBtn.getStyleClass().add("mcgill-button-secondary");
        demoLoginBtn.setPrefWidth(200);
        demoLoginBtn.setOnAction(e -> {
            emailField.setText("trader@mcgill.ca");
            passwordField.setText("demo123");
        });
        
        // Layout
        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER);
        formBox.getChildren().addAll(emailLabel, emailField, passwordLabel, passwordField);
        
        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(loginBtn, demoLoginBtn);
        
        VBox loginBox = new VBox(25);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(40));
        loginBox.getChildren().addAll(title, subtitle, formBox, buttonBox);
        
        // Add slight background color
        loginBox.setStyle("-fx-background-color: #FAFAFA;");
        
        loginScene = new Scene(loginBox);
    }
    
    /**
     * Simple authentication logic
     * In production, this would connect to a database
     */
    private boolean authenticate(String email, String password) {
        // Demo credentials
        if (email.equals("trader@mcgill.ca") && password.equals("demo123")) {
            return true;
        }
        if (email.equals("admin@mcgill.ca") && password.equals("admin123")) {
            return true;
        }
        return false;
    }
    
    /**
     * Create Main Menu Scene
     */
    private void createMainMenu() {
        Label title = new Label("McGill Stock Market Platform");
        title.getStyleClass().add("mcgill-title");
        
        Label subtitle = new Label("Holdings & Analytics");
        subtitle.getStyleClass().add("mcgill-subtitle");
        
        Button Section1Btn = new Button("Portfolio Management");
        Section1Btn.setMaxWidth(Double.MAX_VALUE);
        Section1Btn.getStyleClass().add("mcgill-button");
        Section1Btn.setOnAction(e -> switchToPortfolio());
        
        Button Section2Btn = new Button("Calculator");
        Section2Btn.setMaxWidth(Double.MAX_VALUE);
        Section2Btn.setOnAction(e -> switchToCalculator());
        
        // Logout button
        Button logoutBtn = new Button("Logout");
        logoutBtn.setMaxWidth(Double.MAX_VALUE);
        logoutBtn.getStyleClass().add("mcgill-button-secondary");
        logoutBtn.setOnAction(e -> logout());
        
        // Exit button
        Button exitBtn = new Button("Exit Application");
        exitBtn.setMaxWidth(Double.MAX_VALUE);
        exitBtn.getStyleClass().add("mcgill-button-back");
        exitBtn.setOnAction(e -> primaryStage.close());
        
        VBox menuBox = new VBox(20, title, subtitle, Section1Btn, Section2Btn, logoutBtn, exitBtn);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(new Insets(40));
        
        mainScene = new Scene(menuBox);
    }
    
    /**
     * Create Calculator Scene - Stock Market Calculator
     */
    private void createCalculator() {
        CalculatorService calcService = new CalculatorService();
        
        // Title
        Label title = new Label("📊 Stock Market Calculator");
        title.setStyle("-fx-font-size: 22pt; -fx-font-weight: bold;");
        
        // Tab 1: Profit/Loss Calculator
        Label pnlTitle = new Label("Profit/Loss Calculator");
        pnlTitle.setStyle("-fx-font-size: 14pt; -fx-font-weight: bold;");
        
        Label buyPriceLabel = new Label("Buy Price ($):");
        TextField buyPriceField = new TextField();
        buyPriceField.setPromptText("e.g., 150.00");
        
        Label sellPriceLabel = new Label("Sell Price ($):");
        TextField sellPriceField = new TextField();
        sellPriceField.setPromptText("e.g., 175.50");
        
        Label sharesLabel = new Label("Number of Shares:");
        TextField sharesField = new TextField();
        sharesField.setPromptText("e.g., 100");
        
        Label resultLabel = new Label("Result:");
        resultLabel.setStyle("-fx-font-size: 12pt; -fx-font-weight: bold;");
        
        Button calculateBtn = new Button("Calculate P/L");
        calculateBtn.getStyleClass().add("mcgill-button");
        calculateBtn.setOnAction(e -> {
            try {
                double buyPrice = Double.parseDouble(buyPriceField.getText());
                double sellPrice = Double.parseDouble(sellPriceField.getText());
                double shares = Double.parseDouble(sharesField.getText());
                
                double pl = calcService.calculateProfitLoss(buyPrice, sellPrice, shares);
                double plPercent = calcService.calculateProfitLossPercent(buyPrice, sellPrice);
                
                String color = pl >= 0 ? "+" : "";
                resultLabel.setText(String.format("Profit/Loss: %s$%.2f (%s%.2f%%)", 
                    color, pl, plPercent >= 0 ? "+" : "", plPercent));
                resultLabel.setStyle(resultLabel.getStyle() + " -fx-text-fill: " + (pl >= 0 ? "#2E7D32" : "#D32F2F") + ";");
            } catch (NumberFormatException ex) {
                showError("Please enter valid numbers!");
            }
        });
        
        GridPane pnlGrid = new GridPane();
        pnlGrid.setHgap(15);
        pnlGrid.setVgap(10);
        pnlGrid.setAlignment(Pos.CENTER);
        pnlGrid.add(buyPriceLabel, 0, 0);
        pnlGrid.add(buyPriceField, 1, 0);
        pnlGrid.add(sellPriceLabel, 0, 1);
        pnlGrid.add(sellPriceField, 1, 1);
        pnlGrid.add(sharesLabel, 0, 2);
        pnlGrid.add(sharesField, 1, 2);
        
        // Tab 2: ROI Calculator
        Label roiTitle = new Label("ROI Calculator");
        roiTitle.setStyle("-fx-font-size: 14pt; -fx-font-weight: bold;");
        
        Label investmentLabel = new Label("Initial Investment ($):");
        TextField investmentField = new TextField();
        investmentField.setPromptText("e.g., 10000");
        
        Label currentValueLabel = new Label("Current Value ($):");
        TextField currentValueField = new TextField();
        currentValueField.setPromptText("e.g., 12000");
        
        Label roiResultLabel = new Label("ROI: 0%");
        roiResultLabel.setStyle("-fx-font-size: 12pt; -fx-font-weight: bold;");
        
        Button roiBtn = new Button("Calculate ROI");
        roiBtn.getStyleClass().add("mcgill-button");
        roiBtn.setOnAction(e -> {
            try {
                double investment = Double.parseDouble(investmentField.getText());
                double current = Double.parseDouble(currentValueField.getText());
                double roi = calcService.calculateROI(investment, current);
                
                String color = roi >= 0 ? "+" : "";
                roiResultLabel.setText(String.format("ROI: %s%.2f%%", color, roi));
                roiResultLabel.setStyle(roiResultLabel.getStyle() + " -fx-text-fill: " + (roi >= 0 ? "#2E7D32" : "#D32F2F") + ";");
            } catch (NumberFormatException ex) {
                showError("Please enter valid numbers!");
            }
        });
        
        GridPane roiGrid = new GridPane();
        roiGrid.setHgap(15);
        roiGrid.setVgap(10);
        roiGrid.setAlignment(Pos.CENTER);
        roiGrid.add(investmentLabel, 0, 0);
        roiGrid.add(investmentField, 1, 0);
        roiGrid.add(currentValueLabel, 0, 1);
        roiGrid.add(currentValueField, 1, 1);
        
        // Layout with tabs
        Tab tab1 = new Tab("Profit/Loss", new VBox(15, pnlTitle, pnlGrid, calculateBtn, resultLabel));
        Tab tab2 = new Tab("ROI", new VBox(15, roiTitle, roiGrid, roiBtn, roiResultLabel));
        
        tab1.setClosable(false);
        tab2.setClosable(false);
        
        TabPane tabPane = new TabPane(tab1, tab2);
        tabPane.setPrefWidth(450);
        
        // Layout
        VBox container = new VBox(20, title, tabPane);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(30));
        
        calculatorScene = new Scene(container);
        calculatorScene.getStylesheets().addAll(
            getClass().getResource("/styles/theme.css").toExternalForm(),
            getClass().getResource("/styles/calculator.css").toExternalForm(),
            getClass().getResource("/styles/common.css").toExternalForm()
        );
    }
    
    /**
     * Create Portfolio Scene - Uses PortfolioController
     * This demonstrates proper MVC separation for stock portfolio
     */
    private void createPortfolio() {
        // Use the controller for this scene with callback for navigation
        PortfolioController controller = new PortfolioController(() -> switchToMainMenu());
        portfolioScene = controller.getScene();
    }
    
    /**
     * Switch to Main Menu (after login)
     */
    private void switchToMainMenu() {
        if (!isLoggedIn) {
            showError("Please login first!");
            return;
        }
        
        if (mainScene == null) {
            createMainMenu();
            
            // Load CSS files
            mainScene.getStylesheets().addAll(
                getClass().getResource("/styles/theme.css").toExternalForm(),
                getClass().getResource("/styles/common.css").toExternalForm()
            );
        }
        
        // Resize window for main application
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(600);
        primaryStage.centerOnScreen();
        
        primaryStage.setScene(mainScene);
        
        // Show welcome message on login
        showWelcomeAlert();
    }
    
    /**
     * Logout - Return to login screen
     */
    private void logout() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Logout");
        confirmAlert.setHeaderText("Confirm Logout");
        confirmAlert.setContentText("Are you sure you want to logout?");
        
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Close calculator window if open
                if (calculatorStage != null) {
                    calculatorStage.close();
                    calculatorStage = null;
                }
                
                // Reset authentication state
                isLoggedIn = false;
                
                // Reset scenes to force fresh login
                mainScene = null;
                calculatorScene = null;
                portfolioScene = null;
                
                // Resize window back to login size
                primaryStage.setWidth(600);
                primaryStage.setHeight(450);
                primaryStage.setMinWidth(600);
                primaryStage.setMinHeight(450);
                primaryStage.centerOnScreen();
                
                // Return to login screen
                primaryStage.setScene(loginScene);
            }
        });
    }
    
    // Scene switching methods
    private void switchToPortfolio() {
        if (!isLoggedIn) {
            showError("Please login first!");
            return;
        }
        
        if (portfolioScene == null) {
            createPortfolio();
        }
        primaryStage.setScene(portfolioScene);
    }
    
    private void switchToCalculator() {
        if (!isLoggedIn) {
            showError("Please login first!");
            return;
        }
        
        // Create a new window for calculator
        if (calculatorStage == null) {
            calculatorStage = new Stage();
            calculatorStage.setTitle("McGill Stock Market Calculator");
            calculatorStage.setWidth(600);
            calculatorStage.setHeight(550);
            calculatorStage.setOnCloseRequest(e -> {
                calculatorStage = null; // Allow reopening
            });
            
            // Create calculator scene if not exists
            if (calculatorScene == null) {
                createCalculator();
            }
            
            calculatorStage.setScene(calculatorScene);
        }
        
        calculatorStage.show();
        calculatorStage.requestFocus(); // Bring to front
    }
    
    // Helper methods
    private void showWelcomeAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Welcome to McGill Stock Market Platform!");
        alert.setHeaderText("Stock Portfolio Management System");
        alert.setContentText("Welcome! This platform demonstrates:\n\n" +
                            "✓ User Authentication\n" +
                            "✓ Real-time Portfolio Tracking\n" +
                            "✓ Profit/Loss Analysis\n" +
                            "✓ MVC Architecture\n" +
                            "✓ Service Layer (Business Logic)\n" +
                            "✓ Repository Pattern (Data Access)\n" +
                            "✓ Professional UI Design\n" +
                            "✓ PostgreSQL Ready\n\n" +
                            "Track your stock holdings with automatic P/L calculations!");
        alert.showAndWait();
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
