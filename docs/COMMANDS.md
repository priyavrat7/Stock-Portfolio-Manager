Command Reference

Run the App
```bash
# From IntelliJ (recommended): Run 'Main'
```

Docker & PostgreSQL
```bash
cd JavaFx
docker-compose up -d        # start DB
docker-compose down         # stop DB
docker ps                   # list containers
docker logs mcgill-stock-postgres | tail -50
```

PostgreSQL Quick Queries
```bash
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "\\dt"
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "SELECT COUNT(*) FROM portfolio;"
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "SELECT symbol,current_price FROM portfolio WHERE symbol='AAPL';"
```

Resources
- Styles: `JavaFx/src/main/resources/styles`
- Images/Fonts: `JavaFx/src/main/resources/{images,fonts}`


