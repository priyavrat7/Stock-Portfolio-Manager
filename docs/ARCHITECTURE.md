Architecture Overview

Layers
- View: JavaFX UI (scenes and controls)
- Controller: UI orchestration (`PortfolioController`)
- Service: business logic (`StockService`, `CalculatorService`, `StockPriceService`)
- Repository: data access (`StockRepositoryPostgreSQL`)
- Model: domain classes (`Stock`)

Key Classes
- `com.mcgill.application.Main` – App entry; handles windows/scenes
- `PortfolioController` – Portfolio screen; table, stats, refresh
- `StockService` – Portfolio logic; aggregates values; persists updates
- `StockPriceService` – Yahoo Finance client; fetches live prices
- `StockRepositoryPostgreSQL` – CRUD to PostgreSQL (`portfolio` table)
- `Stock` – JavaFX properties for reactive UI (id, symbol, shares, prices...)

Data Flow
1) On login → Main shows Portfolio
2) Controller loads portfolio via `StockService` → repository → PostgreSQL
3) User clicks Refresh → `StockPriceService` fetches current prices → `StockService.persist` saves new `current_price` to DB
4) UI auto-updates: table cells and portfolio statistics

Windows
- Portfolio in primary Stage
- Calculator in a separate Stage (can be open side-by-side)


