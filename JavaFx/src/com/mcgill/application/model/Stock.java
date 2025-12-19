package com.mcgill.application.model;

import javafx.beans.property.*;
import java.time.LocalDateTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Stock Model Class with JavaFX Properties
 * Represents a stock portfolio holding with real-time tracking
 * Uses JavaFX properties for automatic UI updates
 */
public class Stock {

    // JavaFX properties for reactive UI binding
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty symbol = new SimpleStringProperty();
    private final StringProperty company = new SimpleStringProperty();
    private final IntegerProperty shares = new SimpleIntegerProperty();
    private final DoubleProperty purchasePrice = new SimpleDoubleProperty();
    private final DoubleProperty currentPrice = new SimpleDoubleProperty();
    private final StringProperty sector = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> lastRefreshed = new SimpleObjectProperty<>();
    
    public Stock() {
        // Default constructor
    }
    
    public Stock(int id, String symbol, String company, int shares, double purchasePrice, double currentPrice, String sector) {
        this.id.set(id);
        this.symbol.set(symbol);
        this.company.set(company);
        this.shares.set(shares);
        this.purchasePrice.set(purchasePrice);
        this.currentPrice.set(currentPrice);
        this.sector.set(sector);
    }
    
    // ========== ID Property ==========
    public int getId() {
        return id.get();
    }
    
    public void setId(int value) {
        id.set(value);
    }
    
    public IntegerProperty idProperty() {
        return id;
    }
    
    // ========== Symbol Property ==========
    public String getSymbol() {
        return symbol.get();
    }
    
    public void setSymbol(String value) {
        symbol.set(value);
    }
    
    public StringProperty symbolProperty() {
        return symbol;
    }
    
    // ========== Company Property ==========
    public String getCompany() {
        return company.get();
    }
    
    public void setCompany(String value) {
        company.set(value);
    }
    
    public StringProperty companyProperty() {
        return company;
    }
    
    // ========== Shares Property ==========
    public int getShares() {
        return shares.get();
    }
    
    public void setShares(int value) {
        shares.set(value);
    }
    
    public IntegerProperty sharesProperty() {
        return shares;
    }
    
    // ========== Purchase Price Property ==========
    public double getPurchasePrice() {
        return purchasePrice.get();
    }
    
    public void setPurchasePrice(double value) {
        purchasePrice.set(value);
    }
    
    public DoubleProperty purchasePriceProperty() {
        return purchasePrice;
    }
    
    // ========== Current Price Property ==========
    public double getCurrentPrice() {
        return currentPrice.get();
    }
    
    public void setCurrentPrice(double value) {
        currentPrice.set(value);
    }
    
    public DoubleProperty currentPriceProperty() {
        return currentPrice;
    }
    
    // ========== Sector Property ==========
    public String getSector() {
        return sector.get();
    }
    
    public void setSector(String value) {
        sector.set(value);
    }
    
    public StringProperty sectorProperty() {
        return sector;
    }
    
    // ========== Last Refreshed Timestamp ==========
    public LocalDateTime getLastRefreshed() {
        return lastRefreshed.get();
    }
    
    public void setLastRefreshed(LocalDateTime value) {
        lastRefreshed.set(value);
    }
    
    public ObjectProperty<LocalDateTime> lastRefreshedProperty() {
        return lastRefreshed;
    }
    
    public String getLastRefreshedFormatted() {
        LocalDateTime ts = getLastRefreshed();
        if (ts == null) return "-";
        ZoneId ny = ZoneId.of("America/New_York");
        return ts.atZone(ny).format(DateTimeFormatter.ofPattern("dd/MM/yy HH/mm/ss"));
    }
    
    // ========== Calculated Properties ==========
    
    /**
     * Calculate total value of holding
     * @return shares × currentPrice
     */
    public double getTotalValue() {
        return shares.get() * currentPrice.get();
    }
    
    /**
     * Calculate profit/loss amount
     * @return (currentPrice - purchasePrice) × shares
     */
    public double getProfitLoss() {
        return (currentPrice.get() - purchasePrice.get()) * shares.get();
    }
    
    /**
     * Calculate profit/loss percentage
     * @return ((currentPrice - purchasePrice) / purchasePrice) × 100
     */
    public double getProfitLossPercent() {
        if (purchasePrice.get() == 0) return 0.0;
        return ((currentPrice.get() - purchasePrice.get()) / purchasePrice.get()) * 100.0;
    }
    
    /**
     * Get formatted profit/loss with sign
     */
    public String getProfitLossFormatted() {
        double pl = getProfitLoss();
        return String.format("%s$%.2f", pl >= 0 ? "+" : "", pl);
    }
    
    /**
     * Get formatted profit/loss percentage with sign
     */
    public String getProfitLossPercentFormatted() {
        double plpct = getProfitLossPercent();
        return String.format("%s%.2f%%", plpct >= 0 ? "+" : "", plpct);
    }
}

