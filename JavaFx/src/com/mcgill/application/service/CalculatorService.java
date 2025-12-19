package com.mcgill.application.service;

/**
 * CalculatorService - Business Logic Layer
 * Handles calculator operations and validations
 */
public class CalculatorService {
    
    /**
     * Calculate result based on operation
     */
    public double calculate(String operation, double num1, double num2) throws ArithmeticException {
        switch (operation) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("Cannot divide by zero!");
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }
    }
    
    /**
     * Validate calculator inputs
     */
    public String validateInputs(String num1Str, String num2Str) {
        if (num1Str == null || num1Str.trim().isEmpty()) {
            return "Please enter the first number!";
        }
        if (num2Str == null || num2Str.trim().isEmpty()) {
            return "Please enter the second number!";
        }
        
        try {
            Double.parseDouble(num1Str);
            Double.parseDouble(num2Str);
            return null; // Valid
        } catch (NumberFormatException ex) {
            return "Please enter valid numbers!";
        }
    }
    
    // ========== STOCK MARKET CALCULATIONS ==========
    
    /**
     * Calculate Profit/Loss amount
     * @param buyPrice Purchase price per share
     * @param sellPrice Current/sell price per share
     * @param shares Number of shares
     * @return Profit/Loss amount
     */
    public double calculateProfitLoss(double buyPrice, double sellPrice, double shares) {
        return (sellPrice - buyPrice) * shares;
    }
    
    /**
     * Calculate Profit/Loss percentage
     * @param buyPrice Purchase price per share
     * @param sellPrice Current/sell price per share
     * @return Profit/Loss percentage
     */
    public double calculateProfitLossPercent(double buyPrice, double sellPrice) {
        if (buyPrice == 0) return 0.0;
        return ((sellPrice - buyPrice) / buyPrice) * 100.0;
    }
    
    /**
     * Calculate ROI (Return on Investment)
     * @param initialInvestment Total money invested
     * @param currentValue Current portfolio value
     * @return ROI percentage
     */
    public double calculateROI(double initialInvestment, double currentValue) {
        if (initialInvestment == 0) return 0.0;
        return ((currentValue - initialInvestment) / initialInvestment) * 100.0;
    }
    
    /**
     * Calculate break-even price (with fees)
     * @param buyPrice Purchase price per share
     * @param fees Trading fees (total)
     * @param shares Number of shares
     * @return Break-even price per share
     */
    public double calculateBreakEven(double buyPrice, double fees, double shares) {
        if (shares == 0) return 0.0;
        return buyPrice + (fees / shares);
    }
    
    /**
     * Calculate position size based on risk
     * @param accountBalance Total account balance
     * @param riskPercent Risk percentage (e.g., 2% = 2.0)
     * @param entryPrice Price per share
     * @return Number of shares to buy
     */
    public int calculatePositionSize(double accountBalance, double riskPercent, double entryPrice) {
        if (entryPrice == 0) return 0;
        double riskAmount = accountBalance * (riskPercent / 100.0);
        return (int) (riskAmount / entryPrice);
    }
    
    /**
     * Calculate total investment value
     * @param pricePerShare Price per share
     * @param numberOfShares Number of shares
     * @return Total investment value
     */
    public double calculateTotalInvestment(double pricePerShare, double numberOfShares) {
        return pricePerShare * numberOfShares;
    }
    
    /**
     * Calculate average down price
     * @param price1 First purchase price
     * @param shares1 First purchase shares
     * @param price2 Second purchase price
     * @param shares2 Second purchase shares
     * @return Average price per share
     */
    public double calculateAveragePrice(double price1, double shares1, double price2, double shares2) {
        double totalCost = (price1 * shares1) + (price2 * shares2);
        double totalShares = shares1 + shares2;
        if (totalShares == 0) return 0.0;
        return totalCost / totalShares;
    }
}

