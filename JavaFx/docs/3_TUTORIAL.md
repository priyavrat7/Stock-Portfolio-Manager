# JavaFX Complete Tutorial

## Table of Contents
1. [Introduction](#introduction)
2. [JavaFX Architecture](#javafx-architecture)
3. [Setting Up JavaFX](#setting-up-javafx)
4. [Core Concepts](#core-concepts)
5. [Building Your First Application](#building-your-first-application)
6. [Key Concepts](#key-concepts-for-interview)

---

## Introduction

### What is JavaFX?
JavaFX is a modern Java library for building Rich Internet Applications (RIA) and desktop applications. It replaced Swing as the primary UI toolkit for Java.

### Why JavaFX?
- **Professional UI**: Create enterprise-grade desktop applications
- **Data-Driven**: Excellent for financial data visualization
- **Cross-Platform**: Runs on Windows, macOS, and Linux
- **Modern APIs**: Scene Builder, FXML, CSS styling

---

## JavaFX Architecture

```
Application
    ↓
Stage (Window)
    ↓
Scene (Content Container)
    ↓
Scene Graph (Node Hierarchy)
    ├── Parent Nodes (Layouts)
    └── Leaf Nodes (Controls)
```

**Key Components:**
- **Stage**: The main window
- **Scene**: The content holder
- **Node**: UI components (buttons, tables, labels, etc.)
- **Parent**: Container nodes (layouts like VBox, HBox, GridPane)
- **FXML**: XML-based UI description (optional but recommended)

---

## Setting Up JavaFX

### JavaFX vs JavaFX SDK
Modern Java (11+) requires external JavaFX dependencies:
- **JavaFX 11+**: Separate library (modular approach)
- **Old Java**: Built-in (Java 8 and earlier)

### Project Structure
```
JavaFx/
├── src/
│   └── Main.java
├── lib/              (JavaFX libraries)
└── out/             (compiled classes)
```

---

## Core Concepts

### 1. The Application Class
Every JavaFX app extends `Application`:

```java
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Your UI code here
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
```

### 2. Stage (Window)
- Represents a window
- Primary stage is created by JavaFX runtime
- Properties: title, size, resizable, etc.

### 3. Scene (Content Area)
- Contains all UI nodes
- Has dimensions (width x height)
- Attached to a stage

### 4. Nodes (UI Components)
- **Control Nodes**: Button, Label, TextField, TableView, etc.
- **Layout Nodes**: VBox, HBox, GridPane, BorderPane, etc.

### 5. Layout Managers

#### VBox (Vertical Layout)
```java
VBox vbox = new VBox(10); // 10px spacing
vbox.getChildren().addAll(button1, button2);
vbox.setAlignment(Pos.CENTER);
```

#### HBox (Horizontal Layout)
```java
HBox hbox = new HBox(10);
hbox.getChildren().addAll(label, textField, button);
```

#### GridPane (Grid Layout)
```java
GridPane grid = new GridPane();
grid.add(label, 0, 0);
grid.add(textField, 1, 0);
grid.setHgap(10);
grid.setVgap(10);
```

#### BorderPane (5 Regions)
```java
BorderPane borderPane = new BorderPane();
borderPane.setTop(topBar);
borderPane.setCenter(contentArea);
borderPane.setBottom(statusBar);
```

### 6. Event Handling
```java
button.setOnAction(e -> {
    // Handle click event
});

textField.setOnKeyPressed(e -> {
    if (e.getCode() == KeyCode.ENTER) {
        // Handle Enter key
    }
});
```

### 7. CSS Styling
JavaFX supports CSS for styling:
```java
label.setStyle("-fx-font-size: 16pt; -fx-text-fill: blue;");
```

Or in external CSS file:
```css
.button {
    -fx-background-color: #4CAF50;
    -fx-text-fill: white;
}
```

---

## Building Your First Application

### Example 1: Hello World
```java
public class HelloWorld extends Application {
    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello JavaFX!");
        Scene scene = new Scene(label, 300, 100);
        stage.setScene(scene);
        stage.setTitle("My First App");
        stage.show();
    }
}
```

### Example 2: Calculator-Like App
```java
public class Calculator extends Application {
    @Override
    public void start(Stage stage) {
        TextField num1 = new TextField();
        TextField num2 = new TextField();
        Button addButton = new Button("Add");
        Label result = new Label("Result: ");
        
        addButton.setOnAction(e -> {
            int sum = Integer.parseInt(num1.getText()) + 
                     Integer.parseInt(num2.getText());
            result.setText("Result: " + sum);
        });
        
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(num1, num2, addButton, result);
        vbox.setPadding(new Insets(10));
        
        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.show();
    }
}
```

---

## Key Concepts

### 1. **Property Binding**
```java
// Automatic updates
label.textProperty().bind(textField.textProperty());
// With transformation
circle.radiusProperty().bind(slider.valueProperty());
```

### 2. **Observable Collections**
```java
ObservableList<String> items = FXCollections.observableArrayList();
items.add("Item 1");
items.add("Item 2");
ListView<String> listView = new ListView<>(items);
// Changes to items automatically reflect in listView
```

### 3. **TableView (Important for Finance)**
```java
TableView<Person> table = new TableView<>();
TableColumn<Person, String> nameCol = new TableColumn<>("Name");
nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
table.getColumns().add(nameCol);
```

### 4. **FXML (UI Design)**
```xml
<!-- MainWindow.fxml -->
<VBox xmlns="http://javafx.com/javafx">
    <Label text="Welcome"/>
    <Button text="Click Me"/>
</VBox>
```

Load FXML:
```java
Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
Scene scene = new Scene(root);
```

### 5. **Model-View-Controller (MVC)**
- **Model**: Data classes
- **View**: FXML or programmatic UI
- **Controller**: Event handlers and business logic

### 6. **Thread Safety**
```java
Platform.runLater(() -> {
    // UI updates must be on JavaFX Application Thread
    label.setText("Updated from background thread");
});
```

### 7. **Scenes and Scene Switching**
```java
// Navigate between scenes
Button button = new Button("Go to Scene 2");
button.setOnAction(e -> stage.setScene(scene2));
```

### 8. **Dialogs**
```java
Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
alert.setTitle("Confirm");
alert.setContentText("Are you sure?");
Optional<ButtonType> result = alert.showAndWait();
```

---

## Common Questions

### What's the difference between Swing and JavaFX?
- Swing: Older, heavier, less modern
- JavaFX: Modern, better graphics, CSS styling, better performance

### The JavaFX Thread
JavaFX has its own thread (JavaFX Application Thread) for UI updates. All UI operations must happen on this thread.

### How to update UI from background thread?
Use `Platform.runLater(() -> { // UI code here })`

### What is FXML?
XML-based UI description language for JavaFX, allows separation of UI design from business logic.

### What are Properties in JavaFX?
Observable wrappers around values that allow automatic UI updates when data changes (binding).

---

## Next Steps

1. Practice building the example projects
2. Learn about TableView for displaying data
3. Understand FXML and Scene Builder
4. Practice event handling
5. Study property binding and observable collections

---

Happy coding! 🚀
