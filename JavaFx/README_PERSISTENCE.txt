DATA PERSISTENCE GUIDE:

Current Setup: IN-MEMORY STORAGE
- Data is lost when you close the application
- Data exists only during the session

To Enable Database Persistence:

Option 1: Using Docker (Recommended)
1. Start Docker Desktop
2. Run: cd /Users/priv/Codes_On_Git/GUI/JavaFx && docker-compose up -d
3. Edit StockService.java:
   - Change line 23 to: repository = new StockRepositoryPostgreSQL();
4. Rebuild and run the application
5. Data will now persist in PostgreSQL database

Option 2: Keep Current Setup
- Just rebuild and run
- Data will be lost on restart (but app works without Docker)

Your scrollbar fix is active - you can now scroll to see all buttons!
