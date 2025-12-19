Database & Persistence

PostgreSQL via Docker
- Service name: `mcgill-stock-postgres`
- Port mapping: host 5433 → container 5432
- Credentials: user `mcgill_user`, db `stock_portfolio`, password `mcgill123`

Start/Stop
```bash
cd JavaFx
docker-compose up -d     # start
docker-compose down      # stop
```

Schema
Table: `public.portfolio`
- id SERIAL PRIMARY KEY
- symbol VARCHAR(10) NOT NULL
- company VARCHAR(100) NOT NULL
- shares INTEGER NOT NULL
- purchase_price DECIMAL(10,2) NOT NULL
- current_price DECIMAL(10,2) NOT NULL
- sector VARCHAR(50)
- date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP
Indexes: on `symbol`, `sector`

Sample Data
- Loaded from `JavaFx/init.sql` on first run
- 12 well-known symbols (AAPL, MSFT, GOOGL, AMZN, TSLA, META, NVDA, JPM, BA, CAT, XOM, V)

Verify Data
```bash
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "SELECT COUNT(*) FROM portfolio;"
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "SELECT * FROM portfolio LIMIT 5;"
```

Persistence
- When you click Refresh, new `current_price` values are written back to PostgreSQL using `StockService.persist()`


