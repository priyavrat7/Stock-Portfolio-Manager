package com.mcgill.application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Manager
 * Singleton pattern for managing PostgreSQL connections
 */
public class DatabaseConnection {
    
    private static DatabaseConnection instance;
    private Connection connection;
    
    // Database credentials
    private static final String URL = "jdbc:postgresql://localhost:5433/stock_portfolio";
    private static final String USER = "mcgill_user";
    private static final String PASSWORD = "mcgill123";
    
    private DatabaseConnection() {
        try {
            // Load PostgreSQL driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL driver not found!");
            e.printStackTrace();
        }
    }
    
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    /**
     * Get database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✓ Connected to PostgreSQL database");
        }
        return connection;
    }
    
    /**
     * Close database connection
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
    
    /**
     * Test database connection
     */
    public boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            return false;
        }
    }
}