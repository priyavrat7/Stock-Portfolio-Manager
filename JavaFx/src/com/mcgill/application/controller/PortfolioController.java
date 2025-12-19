package com.mcgill.application.controller;

import com.mcgill.application.model.Stock;
import com.mcgill.application.service.StockService;
import com.mcgill.application.service.StockPriceService;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * PortfolioController - Handles Stock Portfolio Management UI and Events
 * MVC Controller Layer - Delegates business logic to StockService
 */
public class PortfolioController {
    
    private Scene scene;

    private StockPriceService stockPriceService;
    private TableView<Stock> table;
    private StockService stockService;
    
    // UI Components
    private TextField idField;
    private TextField symbolField;
    private TextField companyField;
    private TextField sharesField;
    private TextField purchasePriceField;
    private TextField currentPriceField;
    private TextField sectorField;
    
    // Statistics labels
    private Label totalInvestmentLabel;
    private Label currentValueLabel;
    private Label profitLossLabel;
    
    // Callback for navigation
    private Runnable onBackCallback;

    public PortfolioController(Runnable onBackCallback) {
        this.onBackCallback = onBackCallback;
        stockService = new StockService();
        stockPriceService = new StockPriceService();
        createScene();
    }
    
    public Scene getScene() {
        return scene;
    }
    
    private void createScene() {
        // Title
        Label title = new Label("McGill Stock Market Portfolio");
        title.getStyleClass().add("mcgill-section-title");
        
        // Portfolio Statistics
        HBox statsBox = new HBox(30);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setPadding(new Insets(15, 20, 15, 20));
        statsBox.getStyleClass().add("stats-bar");
        
        Label totalInvestmentLabel = new Label();
        totalInvestmentLabel.setId("totalInvestment");
        Label currentValueLabel = new Label();
        currentValueLabel.setId("currentValue");
        Label profitLossLabel = new Label();
        profitLossLabel.setId("profitLoss");
        
        // Date label (EDT)
        Label dateLabel = new Label();
        dateLabel.setStyle("-fx-font-weight: bold;");
        java.time.ZoneId nyZone = java.time.ZoneId.of("America/New_York");
        java.time.format.DateTimeFormatter dateFmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yy");
        dateLabel.setText("Date: " + java.time.ZonedDateTime.now(nyZone).format(dateFmt));
        
        // View Graph Button
        Button viewGraphBtn = new Button("📊 View Graph");
        viewGraphBtn.getStyleClass().add("mcgill-button-secondary");
        viewGraphBtn.setPrefHeight(35);
        viewGraphBtn.setOnAction(e -> showPortfolioChart());
        

        // Refresh button
        Button refreshPricesBtn = new Button("🔄 Refresh Prices");
        refreshPricesBtn.getStyleClass().add("mcgill-button-secondary");
        refreshPricesBtn.setPrefHeight(35);
        refreshPricesBtn.setOnAction(e -> refreshStockPrices());

        // Real-Time Analysis (premium) button
        Button realTimeBtn = new Button("⚡ Real-Time Analysis");
        realTimeBtn.getStyleClass().add("mcgill-button");
        realTimeBtn.setPrefHeight(35);
        realTimeBtn.setOnAction(e -> openRealTimeAnalysisWindow());

        // include in statsBox with date
        statsBox.getChildren().addAll(totalInvestmentLabel, currentValueLabel, profitLossLabel, dateLabel, viewGraphBtn, refreshPricesBtn, realTimeBtn);
        
        // Store labels as instance variables for updates
        this.totalInvestmentLabel = totalInvestmentLabel;
        this.currentValueLabel = currentValueLabel;
        this.profitLossLabel = profitLossLabel;
        
        updatePortfolioStats();

        // Auto-refresh prices on load to ensure DB and UI show real-time values
        // This runs asynchronously and persists new prices to PostgreSQL
        refreshStockPrices();


        
        // Create table
        table = new TableView<>();
        
        // Table Columns for Stock Portfolio
        TableColumn<Stock, Number> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        idCol.setPrefWidth(60);
        {
            javafx.scene.control.Label h = new javafx.scene.control.Label("ID");
            h.setWrapText(true);
            h.setMaxWidth(60);
            h.setStyle("-fx-font-weight: bold;");
            idCol.setText("");
            idCol.setGraphic(h);
        }
        
        TableColumn<Stock, String> symbolCol = new TableColumn<>("Symbol");
        symbolCol.setCellValueFactory(cellData -> cellData.getValue().symbolProperty());
        symbolCol.setPrefWidth(80);
        {
            javafx.scene.control.Label h = new javafx.scene.control.Label("Symbol");
            h.setWrapText(true);
            h.setMaxWidth(80);
            h.setStyle("-fx-font-weight: bold;");
            symbolCol.setText("");
            symbolCol.setGraphic(h);
        }
        
        TableColumn<Stock, String> companyCol = new TableColumn<>("Company");
        companyCol.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
        companyCol.setPrefWidth(200);
        {
            javafx.scene.control.Label h = new javafx.scene.control.Label("Company");
            h.setWrapText(true);
            h.setMaxWidth(200);
            h.setStyle("-fx-font-weight: bold;");
            companyCol.setText("");
            companyCol.setGraphic(h);
        }
        
        TableColumn<Stock, Number> sharesCol = new TableColumn<>("Shares");
        sharesCol.setCellValueFactory(cellData -> cellData.getValue().sharesProperty());
        sharesCol.setPrefWidth(80);
        {
            javafx.scene.control.Label h = new javafx.scene.control.Label("Shares");
            h.setWrapText(true);
            h.setMaxWidth(80);
            h.setStyle("-fx-font-weight: bold;");
            sharesCol.setText("");
            sharesCol.setGraphic(h);
        }
        
        TableColumn<Stock, Number> purchaseCol = new TableColumn<>("Purchase Price");
        purchaseCol.setCellValueFactory(cellData -> cellData.getValue().purchasePriceProperty());
        purchaseCol.setPrefWidth(120);
        {
            javafx.scene.control.Label h = new javafx.scene.control.Label("Purchase\nPrice");
            h.setWrapText(true);
            h.setMaxWidth(120);
            h.setStyle("-fx-font-weight: bold;");
            purchaseCol.setText("");
            purchaseCol.setGraphic(h);
        }
        
        TableColumn<Stock, Number> currentCol = new TableColumn<>("Current Price");
        currentCol.setCellValueFactory(cellData -> cellData.getValue().currentPriceProperty());
        currentCol.setPrefWidth(120);
        {
            javafx.scene.control.Label h = new javafx.scene.control.Label("Current\nPrice");
            h.setWrapText(true);
            h.setMaxWidth(120);
            h.setStyle("-fx-font-weight: bold;");
            currentCol.setText("");
            currentCol.setGraphic(h);
        }
        
        // Calculated Value Column
        TableColumn<Stock, String> valueCol = new TableColumn<>("Total Value");
        valueCol.setCellValueFactory(cellData -> {
            Stock stock = cellData.getValue();
            double value = stock.getTotalValue();
            return new javafx.beans.property.SimpleStringProperty(String.format("$%.2f", value));
        });
        valueCol.setPrefWidth(120);
        {
            javafx.scene.control.Label h = new javafx.scene.control.Label("Total\nValue");
            h.setWrapText(true);
            h.setMaxWidth(120);
            h.setStyle("-fx-font-weight: bold;");
            valueCol.setText("");
            valueCol.setGraphic(h);
        }
        
        // Calculated Profit/Loss Column
        TableColumn<Stock, String> plCol = new TableColumn<>("P/L");
        plCol.setCellValueFactory(cellData -> {
            Stock stock = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(stock.getProfitLossFormatted());
        });
        plCol.setPrefWidth(100);
        {
            javafx.scene.control.Label h = new javafx.scene.control.Label("P/L");
            h.setWrapText(true);
            h.setMaxWidth(100);
            h.setStyle("-fx-font-weight: bold;");
            plCol.setText("");
            plCol.setGraphic(h);
        }
        
        // Calculated P/L % Column
        TableColumn<Stock, String> plPercentCol = new TableColumn<>("P/L %");
        plPercentCol.setCellValueFactory(cellData -> {
            Stock stock = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(stock.getProfitLossPercentFormatted());
        });
        plPercentCol.setPrefWidth(90);
        {
            javafx.scene.control.Label h = new javafx.scene.control.Label("P/L %");
            h.setWrapText(true);
            h.setMaxWidth(90);
            h.setStyle("-fx-font-weight: bold;");
            plPercentCol.setText("");
            plPercentCol.setGraphic(h);
        }
        
        TableColumn<Stock, String> sectorCol = new TableColumn<>("Sector");
        sectorCol.setCellValueFactory(cellData -> cellData.getValue().sectorProperty());
        sectorCol.setPrefWidth(120);
        {
            javafx.scene.control.Label h = new javafx.scene.control.Label("Sector");
            h.setWrapText(true);
            h.setMaxWidth(120);
            h.setStyle("-fx-font-weight: bold;");
            sectorCol.setText("");
            sectorCol.setGraphic(h);
        }
        
        // Last Refreshed column (formatted) – time only (EDT)
        TableColumn<Stock, String> refreshedCol = new TableColumn<>("Refreshed At");
        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("HH/mm/ss");
        refreshedCol.setCellValueFactory(cellData -> {
            java.time.LocalDateTime t = cellData.getValue().getLastRefreshed();
            if (t == null) return new javafx.beans.property.SimpleStringProperty("-");
            java.time.ZoneId ny = java.time.ZoneId.of("America/New_York");
            String s = t.atZone(ny).format(fmt);
            return new javafx.beans.property.SimpleStringProperty(s);
        });
        refreshedCol.setPrefWidth(120);
        // Wrap header text
        javafx.scene.control.Label refreshedHeader = new javafx.scene.control.Label("Refreshed At\n(HH/mm/ss)");
        refreshedHeader.setWrapText(true);
        refreshedHeader.setMaxWidth(120);
        refreshedCol.setText("");
        refreshedCol.setGraphic(refreshedHeader);

        table.getColumns().addAll(idCol, symbolCol, companyCol, sharesCol, purchaseCol, currentCol, valueCol, plCol, plPercentCol, sectorCol, refreshedCol);
        table.setItems(stockService.getAllStocks());
        
        // Add selection listener to auto-populate form
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Populate form with selected stock data
                idField.setText(String.valueOf(newSelection.getId()));
                symbolField.setText(newSelection.getSymbol());
                companyField.setText(newSelection.getCompany());
                sharesField.setText(String.valueOf(newSelection.getShares()));
                purchasePriceField.setText(String.valueOf(newSelection.getPurchasePrice()));
                currentPriceField.setText(String.valueOf(newSelection.getCurrentPrice()));
                sectorField.setText(newSelection.getSector());
            }
        });
        
        // Input form - Organized in 3 columns for better layout
        Label formTitle = new Label("Add Stock to Portfolio");
        formTitle.getStyleClass().add("mcgill-subtitle");
        formTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");
        
        // Left Column with bold labels
        Label idLabel = new Label("Stock ID:");
        idLabel.setMinWidth(100);
        idLabel.setMaxWidth(100);
        idLabel.setStyle("-fx-font-weight: bold;");
        idField = new TextField();
        idField.setPromptText("Enter Stock ID");
        
        Label symbolLabel = new Label("Symbol:");
        symbolLabel.setMinWidth(100);
        symbolLabel.setMaxWidth(100);
        symbolLabel.setStyle("-fx-font-weight: bold;");
        symbolField = new TextField();
        symbolField.setPromptText("e.g., AAPL");
        
        Label companyLabel = new Label("Company:");
        companyLabel.setMinWidth(100);
        companyLabel.setMaxWidth(100);
        companyLabel.setStyle("-fx-font-weight: bold;");
        companyField = new TextField();
        companyField.setPromptText("e.g., Apple Inc.");
        
        Label sharesLabel = new Label("Shares:");
        sharesLabel.setMinWidth(100);
        sharesLabel.setMaxWidth(100);
        sharesLabel.setStyle("-fx-font-weight: bold;");
        sharesField = new TextField();
        sharesField.setPromptText("e.g., 100");
        
        // Right Column with bold labels
        Label purchasePriceLabel = new Label("Purchase Price:");
        purchasePriceLabel.setMinWidth(100);
        purchasePriceLabel.setMaxWidth(100);
        purchasePriceLabel.setStyle("-fx-font-weight: bold;");
        purchasePriceField = new TextField();
        purchasePriceField.setPromptText("e.g., 150.00");
        
        Label currentPriceLabel = new Label("Current Price:");
        currentPriceLabel.setMinWidth(100);
        currentPriceLabel.setMaxWidth(100);
        currentPriceLabel.setStyle("-fx-font-weight: bold;");
        currentPriceField = new TextField();
        currentPriceField.setPromptText("e.g., 175.50");
        
        Label sectorLabel = new Label("Sector:");
        sectorLabel.setMinWidth(100);
        sectorLabel.setMaxWidth(100);
        sectorLabel.setStyle("-fx-font-weight: bold;");
        sectorField = new TextField();
        sectorField.setPromptText("e.g., Technology");
        
        // Use GridPane for proper label-field alignment
        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(10);
        formGrid.setAlignment(Pos.CENTER);
        
        // Left column (0)
        formGrid.add(idLabel, 0, 0);
        formGrid.add(idField, 1, 0);
        formGrid.add(symbolLabel, 0, 1);
        formGrid.add(symbolField, 1, 1);
        formGrid.add(companyLabel, 0, 2);
        formGrid.add(companyField, 1, 2);
        formGrid.add(sharesLabel, 0, 3);
        formGrid.add(sharesField, 1, 3);
        
        // Right column (2)
        formGrid.add(purchasePriceLabel, 2, 0);
        formGrid.add(purchasePriceField, 3, 0);
        formGrid.add(currentPriceLabel, 2, 1);
        formGrid.add(currentPriceField, 3, 1);
        formGrid.add(sectorLabel, 2, 2);
        formGrid.add(sectorField, 3, 2);
        
        // Set field widths
        idField.setPrefWidth(180);
        symbolField.setPrefWidth(180);
        companyField.setPrefWidth(180);
        sharesField.setPrefWidth(180);
        purchasePriceField.setPrefWidth(180);
        currentPriceField.setPrefWidth(180);
        sectorField.setPrefWidth(180);
        
        HBox formBox = new HBox(20);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(15, 0, 0, 0));
        formBox.getChildren().add(formGrid);
        
        // Container for form with background
        VBox formContainer = new VBox(15);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #F9F9F9; -fx-background-radius: 10;");
        formContainer.getChildren().addAll(formTitle, formBox);
        
        // Make formBox centered within container
        formBox.setAlignment(Pos.CENTER);
        
        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button addBtn = new Button("Add Stock");
        addBtn.getStyleClass().add("mcgill-button");
        addBtn.setOnAction(e -> handleAddStock());
        
        Button updateBtn = new Button("Update Stock");
        updateBtn.getStyleClass().add("mcgill-button-secondary");
        updateBtn.setOnAction(e -> handleUpdateStock());
        
        Button sellBtn = new Button("Sell Shares");
        sellBtn.getStyleClass().add("mcgill-button-delete");
        sellBtn.setOnAction(e -> handleSellShares());
        
        buttonBox.getChildren().addAll(addBtn, updateBtn, sellBtn);
        
        // Layout with better organization
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));
        
        // Top section: Title and buttons
        HBox topBox = new HBox(20);
        topBox.setAlignment(Pos.CENTER);
        
        Button backBtnTop = new Button("← Back");
        backBtnTop.getStyleClass().add("mcgill-button-back");
        backBtnTop.setOnAction(e -> {
            if (onBackCallback != null) {
                onBackCallback.run();
            }
        });
        
        topBox.getChildren().addAll(backBtnTop);
        
        // Add all sections to container
        container.getChildren().addAll(topBox, title, statsBox, table);
        
        // Make table grow to fill available space
        VBox.setVgrow(table, Priority.ALWAYS);
        table.setMinHeight(300); // Minimum height
        table.setPrefHeight(450); // Preferred height (will grow with window)
        
        // Add form container
        container.getChildren().add(formContainer);
        
        // Add buttons at bottom
        container.getChildren().add(buttonBox);
        
        // Wrap in ScrollPane to allow scrolling when content is too tall
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        scene = new Scene(scrollPane);
        scene.getStylesheets().addAll(
            getClass().getResource("/styles/theme.css").toExternalForm(),
            getClass().getResource("/styles/portfolio.css").toExternalForm(),
            getClass().getResource("/styles/common.css").toExternalForm()
        );
    }

    private void refreshStockPrices() {
        Alert progressAlert = new Alert(Alert.AlertType.INFORMATION);
        progressAlert.setTitle("Updating Prices");
        progressAlert.setHeaderText("Fetching real-time stock prices...");
        progressAlert.setContentText("Please wait...");
        progressAlert.show();

        Task<Void> task = new Task<Void>() {
            private int updatedCount = 0;
            private int attempted = 0;
            private final StringBuilder updateSummary = new StringBuilder();
            @Override
            protected Void call() {
                // Update only the stocks in your portfolio (already loaded from PostgreSQL)
                for (Stock s : table.getItems()) {
                    attempted++;
                    double newPrice = stockPriceService.getCurrentPrice(s.getSymbol());
                    if (newPrice > 0) {
                        double oldPrice = s.getCurrentPrice();
                        s.setCurrentPrice(newPrice);
                        // persist to DB so it survives restarts
                        s.setLastRefreshed(java.time.LocalDateTime.now());
                        stockService.persist(s);
                        updatedCount++;
                        // Append to summary (limit length)
                        if (updateSummary.length() < 300) {
                            updateSummary.append(s.getSymbol())
                                    .append(": $")
                                    .append(String.format("%.2f", oldPrice))
                                    .append(" → $")
                                    .append(String.format("%.2f", newPrice))
                                    .append("  ");
                        }
                    }
                    try { Thread.sleep(500); } catch (InterruptedException ignored) {}
                }
                return null;
            }

            @Override
            protected void succeeded() {
                progressAlert.close();
                updatePortfolioStats();
                table.refresh();
                if (updatedCount > 0) {
                    showSuccess("Prices updated for " + updatedCount + "/" + attempted + " stocks.\n" + updateSummary);
                } else {
                    showError("No prices were updated. The API may have been rate-limited or returned no data.");
                }
            }

            @Override
            protected void failed() {
                progressAlert.close();
                showError("Failed to update prices. Check your internet connection.");
            }
        };

        new Thread(task).start();
    }
    
    /**
     * Handle Add Stock event
     * Controllers delegate to services for business logic
     */
    private void handleAddStock() {
        String idStr = idField.getText().trim();
        String symbol = symbolField.getText().trim().toUpperCase();
        String company = companyField.getText().trim();
        String sharesStr = sharesField.getText().trim();
        String purchasePriceStr = purchasePriceField.getText().trim();
        String currentPriceStr = currentPriceField.getText().trim();
        String sector = sectorField.getText().trim();
        
        // Validate input
        if (idStr.isEmpty() || symbol.isEmpty() || company.isEmpty() || 
            sharesStr.isEmpty() || purchasePriceStr.isEmpty() || currentPriceStr.isEmpty()) {
            showError("All fields are required!");
            return;
        }
        
        // Set default sector if empty
        if (sector.isEmpty()) {
            sector = "Unknown";
        }
        
        try {
            int id = Integer.parseInt(idStr);
            int shares = Integer.parseInt(sharesStr);
            double purchasePrice = Double.parseDouble(purchasePriceStr);
            double currentPrice = Double.parseDouble(currentPriceStr);
            
            Stock stock = new Stock(id, symbol, company, shares, purchasePrice, currentPrice, sector);
            
            // Delegate to service for business logic
            String errorMessage = stockService.addStock(stock);
            
            if (errorMessage != null) {
                showError(errorMessage);
            } else {
                // Success - update stats and clear fields
                updatePortfolioStats();
                idField.clear();
                symbolField.clear();
                companyField.clear();
                sharesField.clear();
                purchasePriceField.clear();
                currentPriceField.clear();
                sectorField.clear();
            }
        } catch (NumberFormatException ex) {
            showError("Please enter valid numbers for numeric fields!");
        }
    }
    
    /**
     * Handle Update Stock event
     * Updates shares and purchase price of selected stock
     */
    private void handleUpdateStock() {
        Stock selected = table.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showError("Please select a stock to update!");
            return;
        }
        
        String sharesStr = sharesField.getText().trim();
        String purchasePriceStr = purchasePriceField.getText().trim();
        
        // Validate input
        if (sharesStr.isEmpty() || purchasePriceStr.isEmpty()) {
            showError("Please enter new shares and purchase price!");
            return;
        }
        
        try {
            int newShares = Integer.parseInt(sharesStr);
            double newPurchasePrice = Double.parseDouble(purchasePriceStr);
            
            if (newShares <= 0) {
                showError("Shares must be greater than zero!");
                return;
            }
            
            if (newPurchasePrice <= 0) {
                showError("Purchase price must be greater than zero!");
                return;
            }
            
            // Update the stock
            selected.setShares(newShares);
            selected.setPurchasePrice(newPurchasePrice);
            
            // Trigger table refresh to update calculated values
            int selectedIndex = table.getSelectionModel().getSelectedIndex();
            table.refresh();
            
            // Show success
            showSuccess("Stock updated successfully!");
            
            // Update portfolio statistics
            updatePortfolioStats();
            
            // Clear fields
            idField.clear();
            symbolField.clear();
            companyField.clear();
            sharesField.clear();
            purchasePriceField.clear();
            currentPriceField.clear();
            sectorField.clear();
            
        } catch (NumberFormatException ex) {
            showError("Please enter valid numbers for shares and purchase price!");
        }
    }
    
    /**
     * Handle Sell Shares event
     * Allows selling partial shares or all shares
     */
    private void handleSellShares() {
        Stock selected = table.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showError("Please select a stock to sell!");
            return;
        }
        
        // Fetch live price before selling
        double livePrice = stockPriceService.getCurrentPrice(selected.getSymbol());
        if (livePrice <= 0) {
            showError("Could not fetch live price. Please try again in a moment.");
            return;
        }
        selected.setCurrentPrice(livePrice);
        selected.setLastRefreshed(java.time.LocalDateTime.now());
        stockService.persist(selected); // persist so DB/UI align with the sell 
        
        // Show dialog to input number of shares to sell
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Sell Shares");
        dialog.setHeaderText("How many shares to sell?");
        dialog.setContentText(String.format("You own %d shares of %s.\n\nEnter number of shares to sell:", 
            selected.getShares(), selected.getSymbol()));
        
        dialog.showAndWait().ifPresent(sharesStr -> {
            try {
                int sharesToSell = Integer.parseInt(sharesStr.trim());
                int currentShares = selected.getShares();
                
                if (sharesToSell <= 0) {
                    showError("Shares to sell must be greater than zero!");
                    return;
                }
                
                if (sharesToSell > currentShares) {
                    showError(String.format("Cannot sell more than you own! You have %d shares.", currentShares));
                    return;
                }
                
                // Delegate to service
                String errorMessage = stockService.sellShares(selected, sharesToSell);
                
                if (errorMessage != null) {
                    showError(errorMessage);
                } else {
                    // Trigger table refresh to update calculated values (Total Value, P/L)
                    int selectedIndex = table.getSelectionModel().getSelectedIndex();
                    table.refresh();
                    if (selectedIndex >= 0 && selectedIndex < table.getItems().size()) {
                        table.getSelectionModel().clearSelection();
                    }
                    
                    // Success message with sold price/time (EDT)
                    java.time.ZoneId ny = java.time.ZoneId.of("America/New_York");
                    java.time.format.DateTimeFormatter tfmt = java.time.format.DateTimeFormatter.ofPattern("HH/mm/ss");
                    String soldTime = selected.getLastRefreshed() == null ? "-" : selected.getLastRefreshed().atZone(ny).format(tfmt);
                    String soldAt = String.format("$%.2f", selected.getCurrentPrice());
                    if (sharesToSell == currentShares) {
                        showSuccess(String.format("Sold all %d shares of %s at %s (%s)", sharesToSell, selected.getSymbol(), soldAt, soldTime));
                    } else {
                        int remaining = currentShares - sharesToSell;
                        showSuccess(String.format("Sold %d shares of %s at %s (%s). %d shares remaining.", 
                            sharesToSell, selected.getSymbol(), soldAt, soldTime, remaining));
                    }
                    
                    // Update portfolio-level statistics
                    updatePortfolioStats();
                    
                    // Clear form
                    idField.clear();
                    symbolField.clear();
                    companyField.clear();
                    sharesField.clear();
                    purchasePriceField.clear();
                    currentPriceField.clear();
                    sectorField.clear();
                }
            } catch (NumberFormatException ex) {
                showError("Please enter a valid number of shares!");
            }
        });
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Update portfolio statistics labels
     */
    private void updatePortfolioStats() {
        if (totalInvestmentLabel == null || currentValueLabel == null || profitLossLabel == null) {
            return;
        }
        
        double investment = stockService.getTotalInvestment();
        double current = stockService.getTotalPortfolioValue();
        double pl = stockService.getTotalProfitLoss();
        double plPercent = stockService.getTotalProfitLossPercent();
        
        totalInvestmentLabel.setText(String.format("Total Investment: $%,.2f", investment));
        totalInvestmentLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13pt; -fx-text-fill: #333333;");
        
        currentValueLabel.setText(String.format("Current Value: $%,.2f", current));
        currentValueLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13pt; -fx-text-fill: #2196F3;");
        
        String plColor = pl >= 0 ? "#2E7D32" : "#D32F2F";
        profitLossLabel.setText(String.format("P/L: %s$%,.2f (%s%.2f%%)", 
            pl >= 0 ? "+" : "", pl, plPercent >= 0 ? "+" : "", plPercent));
        profitLossLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13pt; -fx-text-fill: " + plColor + ";");
    }
    
    /**
     * Show portfolio pie chart in a new window
     * Displays what percentage of total investment is in each stock
     */
    private void showPortfolioChart() {
        if (stockService.getAllStocks().isEmpty()) {
            showError("No stocks in portfolio to display!");
            return;
        }
        
        // Create new window (Stage)
        Stage chartStage = new Stage();
        chartStage.setTitle("Portfolio Investment Distribution");
        chartStage.setWidth(600);
        chartStage.setHeight(500);
        
        // Create PieChart
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Investment by Stock");
        pieChart.setLabelLineLength(10);
        
        // Calculate total investment
        double totalInvestment = stockService.getTotalInvestment();
        
        // Add data for each stock
        javafx.collections.ObservableList<Stock> stocks = stockService.getAllStocks();
        for (Stock stock : stocks) {
            double stockInvestment = stock.getShares() * stock.getPurchasePrice();
            double percentage = (stockInvestment / totalInvestment) * 100.0;
            
            String label = String.format("%s (%s): $%,.0f (%.1f%%)", 
                stock.getSymbol(), 
                stock.getCompany(), 
                stockInvestment, 
                percentage);
            
            PieChart.Data slice = new PieChart.Data(label, stockInvestment);
            pieChart.getData().add(slice);
        }
        
        // Create close button
        Button closeBtn = new Button("Close");
        closeBtn.getStyleClass().add("mcgill-button");
        closeBtn.setOnAction(e -> chartStage.close());
        
        // Layout
        VBox chartContainer = new VBox(15);
        chartContainer.setAlignment(Pos.CENTER);
        chartContainer.setPadding(new Insets(20));
        chartContainer.getChildren().addAll(pieChart, closeBtn);
        
        Scene chartScene = new Scene(chartContainer);
        chartScene.getStylesheets().addAll(
            getClass().getResource("/styles/theme.css").toExternalForm(),
            getClass().getResource("/styles/common.css").toExternalForm()
        );
        
        chartStage.setScene(chartScene);
        chartStage.show();
    }

    private void openRealTimeAnalysisWindow() {
        Stage rtStage = new Stage();
        rtStage.setTitle("Real-Time Analysis");

        // Table and data
        javafx.collections.ObservableList<com.mcgill.application.model.LiveTick> data = javafx.collections.FXCollections.observableArrayList();
        javafx.scene.control.TableView<com.mcgill.application.model.LiveTick> table = new javafx.scene.control.TableView<>(data);
        table.setColumnResizePolicy(javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        javafx.scene.control.TableColumn<com.mcgill.application.model.LiveTick, String> symCol = new javafx.scene.control.TableColumn<>("Symbol");
        symCol.setCellValueFactory(c -> c.getValue().symbolProperty());
        javafx.scene.control.TableColumn<com.mcgill.application.model.LiveTick, String> timeCol = new javafx.scene.control.TableColumn<>("Time");
        timeCol.setCellValueFactory(c -> c.getValue().timeProperty());
        javafx.scene.control.TableColumn<com.mcgill.application.model.LiveTick, Number> pxCol = new javafx.scene.control.TableColumn<>("Price");
        pxCol.setCellValueFactory(c -> c.getValue().priceProperty());
        table.getColumns().addAll(symCol, timeCol, pxCol);

        // Layout
        VBox box = new VBox(12, table);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(16));
        box.getStyleClass().add("card");

        Scene scene = new Scene(box, 700, 420);
        scene.getStylesheets().addAll(
            getClass().getResource("/styles/theme.css").toExternalForm(),
            getClass().getResource("/styles/common.css").toExternalForm(),
            getClass().getResource("/styles/portfolio.css").toExternalForm()
        );
        rtStage.setScene(scene);
        rtStage.show();

        // Connect to kdb and poll quote table every second
        com.mcgill.application.service.KdbClientService kdb = new com.mcgill.application.service.KdbClientService();
        try {
            kdb.connect("localhost", 5012);
            java.util.concurrent.ScheduledExecutorService ses = java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
            ses.scheduleAtFixedRate(() -> {
                try {
                    Object obj = kdb.exec("select sym,px from quote");
                    if (obj instanceof com.kx.c.Flip f) {
                        String[] sym = (String[]) f.y[0];
                        double[] px = (double[]) f.y[1];
                        javafx.application.Platform.runLater(() -> {
                            for (int i = 0; i < sym.length; i++) {
                                com.mcgill.application.model.LiveTick lt = new com.mcgill.application.model.LiveTick();
                                lt.setSymbol(sym[i]);
                                lt.setPrice(px[i]);
                                lt.setTime(java.time.ZonedDateTime.now(java.time.ZoneId.of("America/New_York"))
                                    .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")));
                                data.add(0, lt);
                                if (data.size() > 1000) data.remove(data.size() - 1);
                            }
                        });
                    }
                } catch (Exception ignored2) {}
            }, 0, 1, java.util.concurrent.TimeUnit.SECONDS);
        } catch (Exception ignored) {}
    }
}

