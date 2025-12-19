Getting Started

Prerequisites
- macOS with Java 21+ (JDK 24 works)
- IntelliJ IDEA (Community or Ultimate)
- Docker Desktop (for PostgreSQL persistence)

Clone and Open
1) Open IntelliJ → File → Open → select `JavaFx` folder
2) IntelliJ detects Maven. Wait for sync to finish (bottom status bar)

Run Configuration (IntelliJ)
1) Run → Edit Configurations → + → Application
2) Name: McGill App
3) Main class: `com.mcgill.application.Main`
4) Use classpath of module: `stock-portfolio`
5) VM options: leave empty (JavaFX dependencies are on classpath via Maven)
6) Run

UI Login
- Demo accounts:
  - trader@mcgill.ca / demo123
  - admin@mcgill.ca / admin123

Start Database (optional, to persist data)
```bash
cd JavaFx
docker-compose up -d
```
The app works without Docker using in-memory data, but data is not persisted.

First Run Tips
- If you see missing JavaFX warnings, reload Maven (right‑click `pom.xml` → Maven → Reload)
- If portfolio is empty, the database will auto-load 12 sample stocks on first startup


