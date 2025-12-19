# JavaFX Tutorial Project

## 🚀 START HERE: GETTING_STARTED.md
**New to this project?** Start with [`GETTING_STARTED.md`](2_GETTING_STARTED.md) for your step-by-step learning path!

## 🎯 Purpose
Comprehensive JavaFX tutorial for enterprise applications and desktop development.

## 📚 What You'll Learn

### Core Concepts Covered:
1. **Application Structure** - Stage, Scene, Nodes
2. **Layout Managers** - VBox, HBox, GridPane, BorderPane
3. **UI Components** - Button, Label, TextField, TableView
4. **Event Handling** - Button clicks, form submissions
5. **Observable Collections** - Dynamic data updates
6. **Professional UI** - CSS styling, responsive design

## 🏃 How to Run

### IntelliJ IDEA Setup (Recommended)

1. **Download JavaFX SDK**
   - Visit: https://openjfx.io/
   - Download JavaFX 22 SDK for macOS (ARM64 for Apple Silicon)
   - Extract to `~/Downloads/javafx-sdk-22`

2. **Configure Run Configuration**
   - Right-click `Main.java` → Run → Edit Configurations
   - Create "Application" configuration
   - **VM options**: `--module-path ~/Downloads/javafx-sdk-22/lib --add-modules javafx.controls`
   - **Working directory**: `$PROJECT_DIR$/JavaFx`
   - Click Apply → OK

3. **Run**
   - Click the green Run button ▶️
   - Application will launch!

## 📖 Project Structure

```
JavaFx/
├── src/
│   ├── Main.java              # Main application with lessons
│   └── Person.java            # Data model with JavaFX properties
├── GETTING_STARTED.md          # ⭐ START HERE - Learning path
├── TUTORIAL.md                 # Comprehensive tutorial
├── QUICK_REFERENCE.md          # Quick review for interviews
└── README.md                   # This file
```

## 🎓 Lessons

### Lesson 1: Simple Calculator
- Demonstrates basic UI components
- Event handling
- Input validation
- CSS styling

### Lesson 2: Data Management
- TableView for displaying data
- Observable collections
- Add/Delete operations
- CRUD operations

## 💼 Key Focus Areas

### Enterprise Application Development:
1. **Data Visualization** - TableView for financial data
2. **Event-Driven Architecture** - Handle user interactions
3. **MVC Pattern** - Separate model, view, and controller
4. **Professional UI** - Clean, enterprise-grade interfaces
5. **Thread Safety** - Platform.runLater() for background updates

## 📝 Key Concepts

### What is JavaFX?
Modern Java library for building desktop applications with rich UI capabilities.

### JavaFX Application Lifecycle
1. Application launches
2. `start()` method called with primary Stage
3. Stage contains Scene
4. Scene contains UI nodes (Scene Graph)
5. User interactions trigger events

### Observable Collections
Collections that automatically update UI when data changes. Essential for data-driven applications.

### Threading in JavaFX
All UI updates must occur on JavaFX Application Thread. Use `Platform.runLater()` to update UI from background threads.

## 🚀 Next Steps

1. Run the application and explore each lesson
2. Read the detailed TUTORIAL.md
3. Practice modifying the code
4. Try building your own feature
5. Study the key concepts highlighted

## 📚 Additional Resources

- Official JavaFX Documentation
- Oracle JavaFX Tutorials
- Community forums and tutorials

## 📧 Support

For questions or issues, refer to the comprehensive TUTORIAL.md file included in this project.

---

## 🎯 File Guide

- **GETTING_STARTED.md** - Start here! Step-by-step learning path
- **TUTORIAL.md** - Complete JavaFX concepts and examples  
- **QUICK_REFERENCE.md** - Quick reference guide
- **README.md** - Project overview (this file)

---

**Happy coding!**
