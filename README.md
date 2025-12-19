# 📊 McGill Stock Market Platform

A professional JavaFX application for stock portfolio management, built with MVC + Service + Repository architecture.

## 🚀 Quick Start

1. **Open in IntelliJ IDEA**
2. **Set Run Configuration:**
   - VM Options: `--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls`
3. **Run** the application ▶️

## 📚 Documentation

All documentation is in the [`docs/`](docs/) folder:

### Getting Started
- **[README](docs/1_README.md)** - Project overview
- **[Getting Started](docs/2_GETTING_STARTED.md)** - Setup instructions
- **[Tutorial](docs/3_TUTORIAL.md)** - JavaFX learning guide

### Reference
- **[Quick Reference](docs/4_QUICK_REFERENCE.md)** - Code examples
- **[Architecture Guide](docs/ARCHITECTURE_GUIDE.md)** - MVC pattern
- **[Reading Order](docs/READING_ORDER.md)** - Study guide

### Project History
- **[Transformation](docs/STOCK_MARKET_TRANSFORMATION_COMPLETE.md)** - Employee → Stock platform
- **[Refactoring](docs/REFACTORING_COMPLETE.md)** - Architecture improvements
- **[CSS Reorganization](docs/CSS_REORGANIZATION_COMPLETE.md)** - Modular styling

### Troubleshooting
- **[Troubleshooting Guide](docs/_TROUBLESHOOTING_GUIDE.md)** - Common issues

## 📂 Project Structure

```
JavaFx/
├── src/                    # Java source code
│   ├── Main.java
│   └── com/mcgill/application/
│       ├── model/          # Data models
│       ├── controller/     # UI controllers
│       ├── service/        # Business logic
│       └── repository/     # Data access
├── resources/              # Frontend assets
│   ├── styles/            # CSS files (modular)
│   ├── images/            # Images & logos
│   └── fonts/             # Custom fonts
├── docs/                   # Documentation 📚
│   ├── 1_README.md
│   ├── 2_GETTING_STARTED.md
│   └── ...
└── README.md              # This file
```

## ✨ Features

- ✅ **Real-time Portfolio Tracking**
- ✅ **Profit/Loss Calculations** (automatic)
- ✅ **MVC Architecture** (professional design)
- ✅ **Service Layer** (business logic separation)
- ✅ **Repository Pattern** (database-ready)
- ✅ **Modular CSS** (4 organized files)
- ✅ **PostgreSQL Ready** (4-table schema designed)

## 🎯 What This Demonstrates

### Architecture
- Model-View-Controller (MVC)
- Service Layer for business logic
- Repository Pattern for data access
- Separation of concerns

### JavaFX
- TableView with 10 columns
- Calculated properties (P/L, P/L%)
- Form validation
- Scene switching
- CSS styling

### Best Practices
- Modular CSS (theme.css, portfolio.css, calculator.css, common.css)
- Organized documentation
- Clean project structure
- Professional code organization

## 🗄️ Database Schema (Ready for PostgreSQL)

```sql
-- 4 tables designed for scalability
portfolio      -- Current holdings
transactions   -- Buy/sell history
traders        -- User accounts
market_data    -- Real-time data
```

## 🎨 Sample Data

12 stocks pre-loaded:
- Technology: AAPL, MSFT, GOOGL, META, NVDA
- Finance: JPM, BAC
- Healthcare: JNJ, PFE
- E-commerce: AMZN
- Automotive: TSLA
- Media: NFLX

## 📞 Support

See [docs/_TROUBLESHOOTING_GUIDE.md](docs/_TROUBLESHOOTING_GUIDE.md) for help.

## 🎉 Status

✅ **Production Ready**  
✅ **PostgreSQL Ready**  
✅ **Fully Functional**

---

**Built with JavaFX | McGIll University Theme**

