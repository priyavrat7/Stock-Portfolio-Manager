Live Prices (Yahoo Finance)

Endpoint
- Yahoo Finance (no key):
  - Single symbol: `https://query1.finance.yahoo.com/v8/finance/chart/{SYMBOL}`
  - Batch quote: `https://query1.finance.yahoo.com/v7/finance/quote?symbols=AAPL,MSFT,...`
- We use the batch quote API to reduce rate limiting and fetch many symbols at once.

Implementation
- `StockPriceService` handles HTTP and JSON parsing (Gson)
- `PortfolioController` calls `refreshStockPrices()` which:
  1) Batches all symbols from the table
  2) Fetches `regularMarketPrice`
  3) Updates each stock’s `currentPrice`
  4) Persists to PostgreSQL via `StockService.persist()`
  5) Refreshes the table and portfolio stats

Notes
- Prices update during market hours; off-hours may be delayed or unchanged
- API is best-effort; if rate-limited, try again after a short pause

Where to modify
- `JavaFx/src/com/mcgill/application/service/StockPriceService.java`
- `JavaFx/src/com/mcgill/application/controller/PortfolioController.java`


