Troubleshooting

JavaFX runtime components are missing
- Add VM option `--add-modules javafx.controls,javafx.graphics` or reload Maven to pull JavaFX jars

Duplicate children added (HBox)
- Ensure UI nodes are added to a container only once (fix duplicate `getChildren().addAll(...)` calls)

DB shows old prices after refresh
- Confirm the refresh dialog says N stocks updated
- Query DB:
  - `docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "SELECT symbol,current_price FROM portfolio WHERE symbol='AAPL';"`
- If unchanged, Yahoo API may have been rate-limited; try again

Docker not running
- Start Docker Desktop
- `open -a Docker`
- `docker-compose up -d`


