-- Initialize McGill Stock Portfolio Database

-- Create Portfolio Table
CREATE TABLE IF NOT EXISTS portfolio (
                                         id SERIAL PRIMARY KEY,
                                         symbol VARCHAR(10) NOT NULL,
    company VARCHAR(100) NOT NULL,
    shares INTEGER NOT NULL,
    purchase_price DECIMAL(10,2) NOT NULL,
    current_price DECIMAL(10,2) NOT NULL,
    sector VARCHAR(50),
    date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Create Indexes for Performance
CREATE INDEX IF NOT EXISTS idx_portfolio_symbol ON portfolio(symbol);
CREATE INDEX IF NOT EXISTS idx_portfolio_sector ON portfolio(sector);

-- Insert Sample Data
INSERT INTO portfolio (symbol, company, shares, purchase_price, current_price, sector) VALUES
                                                                                           ('AAPL', 'Apple Inc.', 100, 150.00, 175.50, 'Technology'),
                                                                                           ('MSFT', 'Microsoft Corp', 50, 380.00, 405.20, 'Technology'),
                                                                                           ('GOOGL', 'Alphabet Inc', 75, 140.00, 145.80, 'Technology'),
                                                                                           ('AMZN', 'Amazon.com Inc', 60, 3200.00, 3400.00, 'E-Commerce'),
                                                                                           ('TSLA', 'Tesla Inc', 200, 800.00, 950.00, 'Automotive'),
                                                                                           ('META', 'Meta Platforms', 80, 320.00, 350.75, 'Social Media'),
                                                                                           ('NVDA', 'NVIDIA Corp', 90, 450.00, 520.00, 'Technology'),
                                                                                           ('JPM', 'JPMorgan Chase', 100, 160.00, 165.00, 'Finance'),
                                                                                           ('BA', 'Boeing Co', 50, 200.00, 210.00, 'Aerospace'),
                                                                                           ('CAT', 'Caterpillar Inc', 40, 240.00, 250.00, 'Industrial'),
                                                                                           ('XOM', 'Exxon Mobil', 70, 85.00, 95.00, 'Energy'),
                                                                                           ('V', 'Visa Inc', 60, 250.00, 275.00, 'Finance')
    ON CONFLICT DO NOTHING;

-- Display success message
SELECT 'McGill Stock Portfolio database initialized successfully!' AS status;