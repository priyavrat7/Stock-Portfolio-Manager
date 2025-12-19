package com.mcgill.application.repository;

import com.mcgill.application.database.DatabaseConnection;
import com.mcgill.application.model.Stock;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PostgreSQL Implementation of StockRepository
 * Replaces in-memory storage with database persistence
 */
public class StockRepositoryPostgreSQL {

    private final DatabaseConnection dbConnection;

    public StockRepositoryPostgreSQL() {
        this.dbConnection = DatabaseConnection.getInstance();
        initializeDatabase();
    }

    /**
     * Initialize database connection and create table if needed
     */
    private void initializeDatabase() {
        try (Connection conn = dbConnection.getConnection()) {
            System.out.println("✓ Database connection established");
        } catch (SQLException e) {
            System.err.println("✗ Failed to connect to database: " + e.getMessage());
        }
    }

    /**
     * Save a stock to the database
     */
    public void save(Stock stock) {
        String sql = "INSERT INTO portfolio (symbol, company, shares, purchase_price, current_price, sector) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, stock.getSymbol());
            pstmt.setString(2, stock.getCompany());
            pstmt.setInt(3, stock.getShares());
            pstmt.setDouble(4, stock.getPurchasePrice());
            pstmt.setDouble(5, stock.getCurrentPrice());
            pstmt.setString(6, stock.getSector());

            pstmt.executeUpdate();
            System.out.println("✓ Stock saved: " + stock.getSymbol());
        } catch (SQLException e) {
            System.err.println("Error saving stock: " + e.getMessage());
        }
    }

    /**
     * Find all stocks in the database
     */
    public List<Stock> findAll() {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM portfolio ORDER BY symbol";

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Stock stock = mapResultSetToStock(rs);
                stocks.add(stock);
            }

            System.out.println("✓ Loaded " + stocks.size() + " stocks from database");
        } catch (SQLException e) {
            System.err.println("Error fetching stocks: " + e.getMessage());
        }

        return stocks;
    }

    /**
     * Find stock by ID
     */
    public Stock findById(int id) {
        String sql = "SELECT * FROM portfolio WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToStock(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding stock: " + e.getMessage());
        }

        return null;
    }

    /**
     * Delete stock by ID
     */
    public void delete(int id) {
        String sql = "DELETE FROM portfolio WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("✓ Stock deleted: ID " + id);
        } catch (SQLException e) {
            System.err.println("Error deleting stock: " + e.getMessage());
        }
    }

    /**
     * Update existing stock
     */
    public void update(Stock stock) {
        String sql = "UPDATE portfolio SET symbol = ?, company = ?, shares = ?, " +
                "purchase_price = ?, current_price = ?, sector = ?, last_refreshed = ? WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, stock.getSymbol());
            pstmt.setString(2, stock.getCompany());
            pstmt.setInt(3, stock.getShares());
            pstmt.setDouble(4, stock.getPurchasePrice());
            pstmt.setDouble(5, stock.getCurrentPrice());
            pstmt.setString(6, stock.getSector());
            if (stock.getLastRefreshed() != null) {
                pstmt.setTimestamp(7, java.sql.Timestamp.valueOf(stock.getLastRefreshed()));
            } else {
                pstmt.setTimestamp(7, null);
            }
            pstmt.setInt(8, stock.getId());

            pstmt.executeUpdate();
            System.out.println("✓ Stock updated: " + stock.getSymbol());
        } catch (SQLException e) {
            System.err.println("Error updating stock: " + e.getMessage());
        }
    }

    /**
     * Check if stock with ID exists
     */
    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM portfolio WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking stock existence: " + e.getMessage());
        }

        return false;
    }

    /**
     * Map ResultSet to Stock object
     */
    private Stock mapResultSetToStock(ResultSet rs) throws SQLException {
        Stock stock = new Stock();
        stock.setId(rs.getInt("id"));
        stock.setSymbol(rs.getString("symbol"));
        stock.setCompany(rs.getString("company"));
        stock.setShares(rs.getInt("shares"));
        stock.setPurchasePrice(rs.getDouble("purchase_price"));
        stock.setCurrentPrice(rs.getDouble("current_price"));
        stock.setSector(rs.getString("sector"));
        java.sql.Timestamp ts = rs.getTimestamp("last_refreshed");
        if (ts != null) stock.setLastRefreshed(ts.toLocalDateTime());
        return stock;
    }
}