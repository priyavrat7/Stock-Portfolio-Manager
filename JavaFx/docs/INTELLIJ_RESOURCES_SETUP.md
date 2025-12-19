# IntelliJ Resources Folder Setup

## 🚨 Issue
The application can't find CSS files because `resources/` isn't marked as a resources folder.

## ✅ Fix - Mark resources/ as Resources Folder

### Step 1: Open Project Structure
1. Right-click on `resources` folder in IntelliJ
2. Select **Mark Directory as** → **Resources Root**
   OR
3. File → Project Structure → Modules → Sources → Mark `resources` as Resources

### Step 2: Verify
After marking as Resources Root, you should see `resources` folder marked with green "Resources" label in IntelliJ.

### Step 3: Rebuild
- Build → Rebuild Project
- Or just run the application again

---

## 📁 Current Structure

```
JavaFx/
├── src/              ← Source code
├── resources/        ← Resources (CSS, images, etc.)
│   ├── styles/
│   ├── images/
│   └── fonts/
└── out/              ← Compiled output
    └── production/
        └── JavaFx/
            └── styles/    ← CSS files should appear here
```

---

## 🎯 Quick Fix (Manual Copy)

I've already manually copied the CSS files to the output directory. Run the app now and it should work!

If you want automatic copying on build, follow the steps above to mark `resources` as Resources Root.

