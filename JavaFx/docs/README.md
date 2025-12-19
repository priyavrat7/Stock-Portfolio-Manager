# 📁 Resources Directory

This directory contains all frontend assets for the McGill Stock Market Platform.

## 📂 Directory Structure

```
resources/
├── styles/           # CSS files - Modular styling
├── images/           # Images, logos, icons (future use)
└── fonts/            # Custom fonts (future use)
```

## 🎨 CSS Architecture

### Modular CSS Files

| File | Purpose | Size | Used By |
|------|---------|------|---------|
| `theme.css` | Colors, global styles, buttons, titles | ~150 lines | All scenes |
| `portfolio.css` | Portfolio table, form styling | ~80 lines | Portfolio scene |
| `calculator.css` | Calculator operation buttons | ~60 lines | Calculator scene |
| `common.css` | Shared utilities, labels, alerts | ~80 lines | All scenes |

### Benefits of Modular CSS:

✅ **Separation of Concerns** - Each module has its own file  
✅ **Maintainability** - Easy to find and update specific styles  
✅ **Scalability** - Add new modules without cluttering one file  
✅ **Team Collaboration** - Multiple developers can work without conflicts  
✅ **Professional Architecture** - Industry best practice  

## 🚀 Usage

### Loading CSS in JavaFX

```java
scene.getStylesheets().addAll(
    getClass().getResource("/styles/theme.css").toExternalForm(),
    getClass().getResource("/styles/portfolio.css").toExternalForm(),
    getClass().getResource("/styles/common.css").toExternalForm()
);
```

## 📊 File Details

### theme.css
- McGill color palette
- Global styles
- Button styles
- Title and header styles

### portfolio.css
- Table view styling
- Portfolio form
- P/L color coding
- Cell alignment

### calculator.css
- Operation buttons (+, -, *, /)
- Result display
- Calculator-specific styling

### common.css
- Text fields
- Labels
- Alert dialogs
- Layout utilities

## 🔄 Future Expansion

```
resources/
├── styles/
│   ├── theme.css
│   ├── portfolio.css
│   ├── calculator.css
│   ├── trading.css         ← Future: Trading module
│   ├── charts.css          ← Future: Chart visualization
│   ├── reports.css         ← Future: Reports module
│   └── common.css
├── images/
│   ├── mcgill-logo.png     ← Future: McGill logo
│   ├── icons/
│   │   ├── chart-icon.png
│   │   └── stock-icon.png
│   └── backgrounds/
└── fonts/
    └── custom-font.ttf
```

## 📝 Migration Notes

**Old location:** `src/mcgill-theme.css` (removed)  
**New location:** `resources/styles/*.css` (modular)

**All Java files have been updated to load from new location!**

