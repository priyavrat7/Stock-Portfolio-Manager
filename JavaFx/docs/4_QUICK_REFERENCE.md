# JavaFX Quick Reference

## 🎯 Essential Concepts

### 1. Application Lifecycle
```java
public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        // UI code here
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
```

### 2. Core Components
- **Stage**: Window container
- **Scene**: Content holder (width x height)
- **Node**: UI components (Button, Label, etc.)
- **Parent**: Container nodes (VBox, HBox, GridPane)

### 3. Common Layouts

#### VBox (Vertical)
```java
VBox vbox = new VBox(10); // 10px spacing
vbox.getChildren().addAll(button1, button2);
vbox.setAlignment(Pos.CENTER);
vbox.setPadding(new Insets(10));
```

#### HBox (Horizontal)
```java
HBox hbox = new HBox(10);
hbox.getChildren().addAll(label, field, button);
```

#### GridPane
```java
GridPane grid = new GridPane();
grid.setHgap(10); grid.setVgap(10);
grid.add(label, 0, 0); // column, row
grid.add(textField, 1, 0);
```

#### BorderPane (5 regions)
```java
BorderPane bp = new BorderPane();
bp.setTop(topBar);
bp.setCenter(content);
bp.setBottom(statusBar);
```

### 4. Event Handling
```java
button.setOnAction(e -> {
    // Handle click
});

textField.setOnKeyPressed(e -> {
    if (e.getCode() == KeyCode.ENTER) {
        // Handle Enter
    }
});
```

### 5. Property Binding ⭐
```java
// One-way binding
label.textProperty().bind(textField.textProperty());

// Automatic updates when property changes
TextField field = new TextField();
Label label = new Label();
label.textProperty().bind(field.textProperty());
// Changing field automatically updates label
```

### 6. Observable Collections ⭐
```java
ObservableList<String> items = FXCollections.observableArrayList();
ListView<String> listView = new ListView<>(items);

// Changes to items automatically reflect in listView
items.add("New item"); // UI updates automatically
```

### 7. TableView (Critical for Finance Apps)
```java
// Model class with JavaFX properties
public class Person {
    private StringProperty name = new SimpleStringProperty();
    
    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }
}

// Create table
TableView<Person> table = new TableView<>();

TableColumn<Person, String> nameCol = new TableColumn<>("Name");
nameCol.setCellValueFactory(cellData -> 
    cellData.getValue().nameProperty());
table.getColumns().add(nameCol);

// Add data
ObservableList<Person> data = FXCollections.observableArrayList();
table.setItems(data);
```

### 8. CSS Styling
```java
// Inline
label.setStyle("-fx-font-size: 16pt; -fx-text-fill: blue;");
button.setStyle("-fx-background-color: #4CAF50;");

// External CSS file
scene.getStylesheets().add("styles.css");
```

### 9. Scene Switching
```java
Button btn = new Button("Next Scene");
btn.setOnAction(e -> {
    stage.setScene(scene2);
});
```

### 10. Alerts & Dialogs
```java
Alert alert = new Alert(Alert.AlertType.INFORMATION);
alert.setTitle("Title");
alert.setContentText("Message");
alert.showAndWait();

// Confirmation
Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
Optional<ButtonType> result = confirm.showAndWait();
if (result.get() == ButtonType.OK) {
    // User clicked OK
}
```

---

## 🎓 Common Questions & Answers

### JavaFX vs Swing
- **Swing**: Older, built into Java, heavier
- **JavaFX**: Modern, separate library (since Java 11), better graphics, CSS styling, better performance

### JavaFX Thread Model
JavaFX runs on JavaFX Application Thread. All UI updates must happen on this thread. For background threads, use `Platform.runLater()`.

```java
// Update UI from background thread
Platform.runLater(() -> {
    label.setText("Updated from background!");
});
```

### JavaFX Properties
Observable wrappers around values that enable automatic UI updates through binding. Key for reactive UIs.

```java
private StringProperty name = new SimpleStringProperty();
public StringProperty nameProperty() { return name; }
```

### MVC in JavaFX
- **Model**: Data classes (e.g., Person, Employee)
- **View**: UI components (FXML or programmatic)
- **Controller**: Event handlers and business logic

### User Input Validation
```java
button.setOnAction(e -> {
    try {
        int age = Integer.parseInt(ageField.getText());
        // Process valid input
    } catch (NumberFormatException ex) {
        showError("Invalid input!");
    }
});
```

### FXML
XML-based UI description language for JavaFX. Separates UI design from business logic.

```java
Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
Scene scene = new Scene(root);
```

### Updating UI from Background Thread
Use `Platform.runLater()`:
```java
new Thread(() -> {
    // Background work
    Platform.runLater(() -> {
        // Update UI safely
    });
}).start();
```

### ObservableList Thread Safety
ObservableList is not thread-safe. JavaFX expects all ObservableList operations to happen on the JavaFX Application Thread. If you modify it from another thread, you must use Platform.runLater() to switch back to the FX thread.

| Aspect | Status |
|--------|--------|
| Thread-safe | Not by default |
| Safe on JavaFX thread | Yes |
| Safe from background threads | Only with Platform.runLater() |

### ObservableList vs ArrayList
ObservableList automatically notifies listeners (like TableView) when data changes. Essential for data-driven UIs.

```java
ObservableList<String> items = FXCollections.observableArrayList();
// Adding/removing items automatically updates UI
```

---

## 💼 Enterprise Development Focus

### Critical Concepts:
1. ✅ **TableView** - Display financial data
2. ✅ **Observable Collections** - Real-time data updates
3. ✅ **Event Handling** - User interactions
4. ✅ **Professional UI Design** - Clean, enterprise interfaces
5. ✅ **Property Binding** - Reactive UI updates
6. ✅ **Thread Safety** - Background operations

### Example: Financial Dashboard
```java
// Display stock prices in a table
TableView<Stock> table = new TableView<>();
// Prices automatically update through ObservableList
// Changes reflect immediately in UI
```

### Best Practices:
- **Separation of Concerns**: Keep logic separate from UI
- **Data Binding**: Use properties for automatic updates
- **Error Handling**: Always validate user input
- **Professional Styling**: Clean, modern UI design
- **Performance**: Use efficient data structures

---

## 📝 Common Patterns

### Pattern 1: Form Submission
```java
Button submit = new Button("Submit");
submit.setOnAction(e -> {
    String name = nameField.getText().trim();
    if (name.isEmpty()) {
        showError("Name required!");
        return;
    }
    // Process form
    processForm(name);
});
```

### Pattern 2: Table CRUD Operations
```java
// Add
button.setOnAction(e -> {
    Person person = new Person(name, email, age);
    data.add(person);
});

// Delete
button.setOnAction(e -> {
    Person selected = table.getSelectionModel().getSelectedItem();
    if (selected != null) {
        data.remove(selected);
    }
});
```

### Pattern 3: Scene Navigation
```java
Button nextBtn = new Button("Next");
nextBtn.setOnAction(e -> {
    if (currentScene == menuScene) {
        stage.setScene(calculatorScene);
    }
});
```

---

## 🚀 Quick Tips

1. **Always validate input** - Check user data before processing
2. **Use properties** - Enable automatic UI updates
3. **Observable collections** - Auto-update UI when data changes
4. **Thread safety** - Use Platform.runLater() for background updates
5. **Clean code** - Separate UI, logic, and data models
6. **Professional styling** - Use CSS for consistent design
7. **Error handling** - Always show user-friendly error messages

---

## 📚 Study Checklist

- [ ] Understand Application, Stage, Scene, Node hierarchy
- [ ] Know all layout managers (VBox, HBox, GridPane, BorderPane)
- [ ] Understand event handling (setOnAction, setOnKeyPressed)
- [ ] Master property binding and JavaFX properties
- [ ] Know ObservableList vs ArrayList
- [ ] Understand TableView and data binding
- [ ] Thread safety with Platform.runLater()
- [ ] CSS styling in JavaFX
- [ ] Scene switching and navigation
- [ ] MVC pattern in JavaFX

---

**Happy coding! You've got this! 💪**
