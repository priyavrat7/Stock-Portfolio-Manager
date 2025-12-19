# 💾 Data Storage Information

## Current Data Storage (Memory Only)

### **Employee Data**
- **Location**: RAM (ObservableList in memory)
- **Persistence**: None - data is lost when application closes
- **Code**: Line 198 in Main.java
  ```java
  ObservableList<Person> data = FXCollections.observableArrayList();
  ```

### **What Happens to Your Data:**
- ✅ Data persists while the application is running
- ❌ Data is lost when you close the application
- ❌ Data is lost when you click "Exit"
- ❌ Data is lost when you rerun the application

## Project Files Location

### **Source Code**
- **Path**: `/Users/priv/Codes_On_Git/GUI/JavaFx/`
- **Files**:
  - `src/Main.java` - Main application code
  - `src/Person.java` - Data model

### **Compiled Classes**
- **Path**: `/Users/priv/Codes_On_Git/GUI/JavaFx/out/production/JavaFx/`
- **Files**: `.class` files (compiled Java code)

### **Documentation**
- **Path**: `/Users/priv/Codes_On_Git/GUI/JavaFx/`
- **Files**: `.md` documentation files

## If You Want to Add File Persistence

### Option 1: JSON File Storage

**Example Code to Add:**

```java
// Save data to file
private void saveToFile(ObservableList<Person> data) {
    try {
        FileWriter writer = new FileWriter("employees.json");
        // Write JSON
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

// Load data from file
private void loadFromFile() {
    try {
        // Read JSON file
        // Parse and add to ObservableList
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

### Option 2: CSV File Storage

**Example Code:**

```java
// Save to employees.csv
private void saveToCSV(ObservableList<Person> data) {
    // Write CSV format
}

// Load from employees.csv
private void loadFromCSV() {
    // Read and parse CSV
}
```

### Option 3: Database (SQLite)

**Example Code:**

```java
// Save to SQLite database
private void saveToDatabase(Person person) {
    // INSERT INTO employees ...
}

// Load from SQLite database
private ObservableList<Person> loadFromDatabase() {
    // SELECT * FROM employees ...
}
```

## Current Application State

### Data Storage: 
- ❌ No file storage
- ❌ No database
- ✅ In-memory only (ObservableList)

### Where Data Lives:
- **Runtime**: RAM (ObservableList)
- **On Disk**: Nothing (no persistence files)

### Default Sample Data:
When you restart the app, you'll always see:
- John Doe, john@morgan.com, 28
- Jane Smith, jane@morgan.com, 32
- Bob Johnson, bob@morgan.com, 45

## Summary

**Data Currently Saved**: ❌ Nothing on disk
**Data Location**: RAM only
**File Location**: `/Users/priv/Codes_On_Git/GUI/JavaFx/`

If you want to add persistent storage, I can help you implement file-based persistence!


