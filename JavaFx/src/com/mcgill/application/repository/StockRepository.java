package com.mcgill.application.repository;

import com.mcgill.application.model.Stock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StockRepository - Data Access Layer for Stock Portfolio
 * Currently uses in-memory storage
 * Ready to be upgraded to PostgreSQL later
 * 
 * Database Schema (Future PostgreSQL Implementation):
 * CREATE TABLE portfolio (
 *     id SERIAL PRIMARY KEY,
 *     symbol VARCHAR(10) NOT NULL,
 *     company VARCHAR(100) NOT NULL,
 *     shares INTEGER NOT NULL,
 *     purchase_price DECIMAL(10,2) NOT NULL,
 *     current_price DECIMAL(10,2) NOT NULL,
 *     sector VARCHAR(50),
 *     date_added TIMESTAMP DEFAULT NOW()
 * );
 */
public class StockRepository {
    
    // In-memory storage (will be replaced with database)
    private final Map<Integer, Stock> portfolio;
    
    public StockRepository() {
        portfolio = new HashMap<>();
        // Initialize with sample stock data
        initializeSampleData();
    }
    
    /**
     * Save a stock to portfolio
     * In future: INSERT INTO portfolio VALUES (...)
     */
    public void save(Stock stock) {
        portfolio.put(stock.getId(), stock);
    }
    
    /**
     * Find all stocks in portfolio
     * In future: SELECT * FROM portfolio
     */
    public List<Stock> findAll() {
        return new ArrayList<>(portfolio.values());
    }
    
    /**
     * Find stock by ID
     * In future: SELECT * FROM portfolio WHERE id = ?
     */
    public Stock findById(int id) {
        return portfolio.get(id);
    }
    
    /**
     * Check if stock with ID exists
     * In future: SELECT COUNT(*) FROM portfolio WHERE id = ?
     */
    public boolean existsById(int id) {
        return portfolio.containsKey(id);
    }
    
    /**
     * Delete stock by ID
     * In future: DELETE FROM portfolio WHERE id = ?
     */
    public void delete(int id) {
        portfolio.remove(id);
    }
    
    /**
     * Update stock (currently same as save)
     * In future: UPDATE portfolio SET ... WHERE id = ?
     */
    public void update(Stock stock) {
        portfolio.put(stock.getId(), stock);
    }
    
    /**
     * Initialize with realistic stock market data
     * Technology, Finance, Healthcare, Retail sectors
     */
    private void initializeSampleData() {
        // Technology Stocks
        save(new Stock(1, "AAPL", "Apple Inc.", 100, 150.00, 175.50, "Technology"));
        save(new Stock(2, "MSFT", "Microsoft Corp.", 50, 300.00, 378.85, "Technology"));
        save(new Stock(3, "GOOGL", "Alphabet Inc.", 25, 120.00, 138.12, "Technology"));
        save(new Stock(4, "META", "Meta Platforms Inc.", 30, 300.00, 322.42, "Technology"));
        save(new Stock(5, "NVDA", "NVIDIA Corp.", 20, 400.00, 479.89, "Technology"));
        
        // Finance Stocks
        save(new Stock(6, "JPM", "JPMorgan Chase & Co.", 40, 140.00, 146.23, "Finance"));
        save(new Stock(7, "BAC", "Bank of America Corp.", 60, 35.00, 34.82, "Finance"));
        
        // Healthcare Stocks
        save(new Stock(8, "JNJ", "Johnson & Johnson", 25, 160.00, 155.67, "Healthcare"));
        save(new Stock(9, "PFE", "Pfizer Inc.", 50, 40.00, 38.45, "Healthcare"));
        
        // Retail/E-Commerce Stocks
        save(new Stock(10, "AMZN", "Amazon.com Inc.", 30, 140.00, 145.83, "E-Commerce"));
        save(new Stock(11, "TSLA", "Tesla Inc.", 75, 200.00, 218.89, "Automotive"));
        
        // Media/Entertainment
        save(new Stock(12, "NFLX", "Netflix Inc.", 20, 400.00, 415.02, "Media"));
    }
}

