package com.mcgill.application.service;

import com.mcgill.application.model.Stock;
import com.mcgill.application.repository.StockRepository;
import com.mcgill.application.repository.StockRepositoryPostgreSQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * StockService - Business Logic Layer for Portfolio Management
 * Handles all business rules and validation for stock holdings
 */
public class StockService {
    
    private StockRepositoryPostgreSQL repository;
    private ObservableList<Stock> portfolio;
    
    public StockService() {
        // Use PostgreSQL repository for persistent storage
        repository = new StockRepositoryPostgreSQL();
        portfolio = FXCollections.observableArrayList();
        loadPortfolio();
    }
    
    /**
     * Add a new stock to portfolio (Business Logic)
     */
    public String addStock(Stock stock) {
        // Business Rule 1: All fields are required
        if (stock.getSymbol() == null || stock.getSymbol().trim().isEmpty()) {
            return "Stock symbol is required!";
        }
        if (stock.getCompany() == null || stock.getCompany().trim().isEmpty()) {
            return "Company name is required!";
        }
        if (stock.getShares() <= 0) {
            return "Number of shares must be greater than zero!";
        }
        if (stock.getPurchasePrice() <= 0) {
            return "Purchase price must be greater than zero!";
        }
        if (stock.getCurrentPrice() <= 0) {
            return "Current price must be greater than zero!";
        }
        
        // Business Rule 2: Stock symbol must be uppercase
        String symbol = stock.getSymbol().toUpperCase();
        stock.setSymbol(symbol);
        
        // Business Rule 3: No duplicate IDs
        if (repository.existsById(stock.getId())) {
            return "Stock with this ID already exists in portfolio!";
        }
        
        // Business Rule 4: Validate symbol format (3-5 uppercase letters)
        if (!stock.getSymbol().matches("^[A-Z]{1,5}$")) {
            return "Stock symbol must be 1-5 uppercase letters!";
        }
        
        // Business Rule 5: Validate prices are reasonable
        if (stock.getPurchasePrice() > 10000 || stock.getCurrentPrice() > 10000) {
            return "Price must be less than $10,000!";
        }
        if (stock.getPurchasePrice() < 0.01 || stock.getCurrentPrice() < 0.01) {
            return "Price must be greater than $0.01!";
        }
        
        // All validations passed - save
        repository.save(stock);
        portfolio.add(stock);
        return null; // Success
    }
    
    /**
     * Delete a stock from portfolio (sell all shares)
     */
    public String deleteStock(Stock stock) {
        if (stock == null) {
            return "Please select a stock to remove from portfolio!";
        }
        
        repository.delete(stock.getId());
        portfolio.remove(stock);
        return null; // Success
    }
    
    /**
     * Sell partial shares of a stock
     * @param stock The stock to sell from
     * @param sharesToSell Number of shares to sell
     * @return Error message if any, null if success
     */
    public String sellShares(Stock stock, int sharesToSell) {
        if (stock == null) {
            return "Please select a stock to sell!";
        }
        
        if (sharesToSell <= 0) {
            return "Shares to sell must be greater than zero!";
        }
        
        int currentShares = stock.getShares();
        
        if (sharesToSell > currentShares) {
            return "Cannot sell more shares than you own! You own " + currentShares + " shares.";
        }
        
        // Update the shares
        int remainingShares = currentShares - sharesToSell;
        stock.setShares(remainingShares);
        
        // If no shares left, remove from portfolio
        if (remainingShares == 0) {
            repository.delete(stock.getId());
            portfolio.remove(stock);
            return null; // Success - removed entirely
        } else {
            // Update in repository
            repository.update(stock);
            return null; // Success - partial sell
        }
    }
    
    /**
     * Get all stocks in portfolio
     */
    public ObservableList<Stock> getAllStocks() {
        return portfolio;
    }
    
    /**
     * Check if stock ID already exists
     */
    public boolean isDuplicateId(int id) {
        return repository.existsById(id);
    }
    
    /**
     * Load all stocks from repository
     */
    private void loadPortfolio() {
        portfolio.clear();
        portfolio.addAll(repository.findAll());
    }

    public void persist(Stock stock) {
        repository.update(stock);
    }
    
    /**
     * Calculate total portfolio value (shares × current price)
     */
    public double getTotalPortfolioValue() {
        return portfolio.stream()
                .mapToDouble(stock -> stock.getShares() * stock.getCurrentPrice())
                .sum();
    }
    
    /**
     * Calculate total investment (shares × purchase price)
     */
    public double getTotalInvestment() {
        return portfolio.stream()
                .mapToDouble(stock -> stock.getShares() * stock.getPurchasePrice())
                .sum();
    }
    
    /**
     * Calculate total profit/loss
     */
    public double getTotalProfitLoss() {
        return portfolio.stream()
                .mapToDouble(Stock::getProfitLoss)
                .sum();
    }
    
    /**
     * Calculate total profit/loss percentage
     */
    public double getTotalProfitLossPercent() {
        double totalInvestment = getTotalInvestment();
        if (totalInvestment == 0) return 0.0;
        return (getTotalProfitLoss() / totalInvestment) * 100.0;
    }
    
    /**
     * Get portfolio statistics as formatted string
     */
    public String getPortfolioSummary() {
        double investment = getTotalInvestment();
        double current = getTotalPortfolioValue();
        double profitLoss = getTotalProfitLoss();
        double profitLossPct = getTotalProfitLossPercent();
        
        return String.format(
            "Total Investment: $%.2f | Current Value: $%.2f | P/L: %s%.2f (%s%.2f%%)",
            investment, current,
            profitLoss >= 0 ? "+" : "", profitLoss,
            profitLossPct >= 0 ? "+" : "", profitLossPct
        );
    }
}

