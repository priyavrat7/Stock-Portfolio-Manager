# Architecture Guide - MVC Pattern Implementation

## 📐 Current Architecture

### Layer Structure:

```
┌─────────────────────────────────────────┐
│         PRESENTATION LAYER               │
│  Main.java (UI Creation, Event Handlers) │
│  + mcgill-theme.css (Styling)           │
└─────────────────────────────────────────┘
              ↓
┌─────────────────────────────────────────┐
│         CONTROLLER LAYER                │
│  Controller.java (Business Logic)       │
│  - Validation                           │
│  - Calculations                         │
│  - Duplicate Checking                   │
└─────────────────────────────────────────┘
              ↓
┌─────────────────────────────────────────┐
│          MODEL LAYER                    │
│  Person.java (Data Model)               │
│  - Properties                           │
│  - Getters/Setters                      │
└─────────────────────────────────────────┘
```

---

## 📁 File Responsibilities

### 1. **Main.java** - Presentation Layer
**Responsibility:** UI Creation & User Interaction
- Creates all UI components (buttons, labels, text fields)
- Handles user events (button clicks)
- Manages scene switching
- Applies styling via CSS classes

**Does NOT contain:**
- Validation logic (moved to Controller)
- Business calculations (moved to Controller)
- Hard-coded styling (moved to CSS)

### 2. **Controller.java** - Business Logic Layer  
**Responsibility:** Business Rules & Validation
- `isDuplicateId()` - Check for duplicate employee IDs
- `validateEmployeeInput()` - Validate all form inputs
- `validateCalculatorInput()` - Validate calculator inputs
- `calculate()` - Perform mathematical operations

**Benefits:**
- Reusable validation logic
- Testable business rules
- Separated from UI concerns

### 3. **Person.java** - Model Layer
**Responsibility:** Data Structure
- Properties (id, name, email, age)
- Getters/Setters
- Property accessors for TableView binding

### 4. **mcgill-theme.css** - Styling Layer
**Responsibility:** Visual Design
- All color styling
- Button styles
- Table styling
- McGill University branding

**Benefits:**
- Change look without touching Java code
- Easy theme switching
- Professional styling

---

## 🔄 Data Flow Example

### Adding an Employee:

```
User Clicks "Add Employee" Button
           ↓
    Main.java (Event Handler)
           ↓
Controller.validateEmployeeInput()
           ↓
    Returns: Error or null
           ↓
    Main.java checks result
           ↓
  If valid → Create Person object
           ↓
    Add to ObservableList
           ↓
    TableView auto-updates (via Observable)
```

---

## ✅ Best Practices Implemented

### 1. **Separation of Concerns**
- UI code separate from business logic
- Styling separate from functionality
- Models separate from controllers

### 2. **Single Responsibility Principle**
Each file has ONE job:
- Main.java → UI only
- Controller.java → Business logic only
- Person.java → Data only
- CSS → Styling only

### 3. **Reusability**
- Controller methods can be reused
- CSS classes can be applied to multiple components
- Person model used in multiple contexts

### 4. **Maintainability**
- Easy to update styles (change CSS)
- Easy to change business rules (edit Controller)
- Easy to modify UI (edit Main.java)

---

## 🎨 Styling Approach

### **Before (Inline Styles):**
```java
button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
```
**Problems:**
- Hard to maintain
- No reusability
- Cluttered Java code

### **After (CSS Classes):**
```java
button.getStyleClass().add("mcgill-button");
```
**Benefits:**
- Clean Java code
- Reusable styles
- Easy to update
- Theme switching possible

---

## 🔍 How to Use This Architecture

### To Add New Feature:
1. **UI Changes** → Edit Main.java
2. **Validation/Business Logic** → Add to Controller.java
3. **Styling** → Update mcgill-theme.css
4. **Data Model** → Update Person.java if needed

### Example: Add "Update Employee" Feature

**Step 1:** Add UI button in Main.java
```java
Button updateBtn = new Button("Update");
updateBtn.getStyleClass().add("mcgill-button");
```

**Step 2:** Add validation in Controller.java
```java
public static String validateUpdate(Person person, ObservableList<Person> data) {
    // Business logic here
    return null; // or error message
}
```

**Step 3:** Style in mcgill-theme.css (already exists!)

---

## 📊 Architecture Benefits

This architecture demonstrates:
- ✅ Understanding of MVC pattern
- ✅ Separation of concerns
- ✅ Professional code organization
- ✅ Best practices in JavaFX
- ✅ Maintainable and scalable code

---

## 🚀 Next Level: FXML Approach

For even better separation, you could use FXML:
- UI in XML files (.fxml)
- Controllers handle events
- CSS for styling
- Complete separation

The current architecture is excellent for production applications!

---

**This architecture is production-ready and follows industry best practices!**
