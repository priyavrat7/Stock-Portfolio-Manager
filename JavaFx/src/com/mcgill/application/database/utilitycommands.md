/*
🌈 **Utility Commands for PostgreSQL Docker Container**

> **🖥️ Container**: `mcgill-stock-postgres`  
> **🗄️ Database**: `stock_portfolio`  
> **👤 User**: `mcgill_user`  
> **🔑 Password**: `mcgill123`  
> **🚪 Port**: `5433`

---

## 🐳 **BASIC DOCKER COMMANDS**

```bash
# 🚀 Start the database container
docker-compose up -d

# ⏹️ Stop the database container
docker-compose down

# 📜 View container logs
docker logs mcgill-stock-postgres

# 🔍 Check container status
docker ps | grep postgres

# ♻️ Restart the database
docker-compose restart
```

---

## 🔗 **DATABASE CONNECTION COMMANDS**

```bash
# 🔌 Connect to PostgreSQL via Docker
docker exec -it mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio

# 📚 List all databases
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "\l"

# 📝 List all tables in current database
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "\dt"

# 🏛️ Show table structure (portfolio)
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "\d portfolio"

# 🔎 Show all columns with data types
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "\d+ portfolio"

# 👥 List all users/roles
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "\du"

# 🗂️ Show all schemas
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "\dn"

# 🔢 List all sequences
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "\ds"
```

---

## 📄 **DATABASE CONTENT COMMANDS**

```bash
# 🗃️ View all records in portfolio table
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "SELECT * FROM portfolio;"

# 🔢 Count total records
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "SELECT COUNT(*) FROM portfolio;"

# 🎚️ View first 5 records
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "SELECT * FROM portfolio LIMIT 5;"

# 🏷️ Get column information
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = 'portfolio';"

# 🌐 View stocks by sector
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "SELECT symbol, company, sector FROM portfolio ORDER BY sector;"
```

---

## ✍️ **MANIPULATION COMMANDS**

```bash
# ➕ Add a new stock (example)
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "INSERT INTO portfolio (symbol, company, shares, purchase_price, current_price, sector) VALUES ('AAPL', 'Apple Inc', 100, 150.00, 175.50, 'Technology');"

# 🔄 Update a stock price (example)
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "UPDATE portfolio SET current_price = 180.00 WHERE symbol = 'AAPL';"

# ⚠️ Delete all records (CAUTION!)
docker exec mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio -c "DELETE FROM portfolio;"

# 🔁 Reset to initial sample data
docker-compose down -v
docker-compose up -d
```

---

## 🧑‍💻 **INTERACTIVE MODE**

```bash
# 🐚 Enter interactive psql shell
docker exec -it mcgill-stock-postgres psql -U mcgill_user -d stock_portfolio
```

> **Once inside, use:**
> - `\dt` — List tables  
> - `\d portfolio` — Show table structure  
> - `\q` — Quit  
> - `SELECT * FROM portfolio;` — View data  

---
*/
