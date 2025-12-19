# Concurrency in this JavaFX App: Synchronous vs. Asynchronous Patterns, Multithreading, and Useful APIs

This document clarifies how and where synchronous vs. asynchronous execution is used in the JavaFX app, what code runs on which thread, and the safe design patterns you should use (with HTTP calls, DB work, timers, and real-time UI updates).

## JavaFX Threading Model: Synchronous vs. Asynchronous (Key Rules)

- **FX Application Thread (UI thread)**  
  - All UI changes **must** happen here (e.g., modifying a `TableView`, changing labels, showing dialogs)  
  - Operations on this thread are generally **synchronous**—JavaFX executes your code in the order called.  
  - **Async → Sync Bridge:** Use `Platform.runLater(...)` from background (async) threads to marshal code back for UI updates (which happen synchronously on the FX thread).

- **Background Work (non‑UI)**  
  - Network I/O (APIs), database queries, heavy computation, and file I/O **should not block** the FX thread—these must be run **asynchronously**.  
  - Use async constructs: `Task`, `Service`, `ExecutorService`, or `ScheduledExecutorService`.


🔵 **Task** *(javafx.concurrent.Task)*

- 🎯 **UI-friendly unit of background work**  
- 💬 Tracks **progress / message / state** — perfect for binding to `ProgressBar`, `Label`, etc.
- 🪄 **Simple UI integration:** Progress and messages auto-connect to JavaFX UI.
- 🌟 **Great for:** One-shot jobs that need built-in UI callbacks or display progress.
- 🚀 **How to run:** Always kick off on a background thread!  
  `new Thread(task).start()`, or submit to an `Executor`.

🟢 **ExecutorService** *(java.util.concurrent)*

- 👥 **Powerful thread-pool manager:** Runs many jobs in parallel, reusing threads.
- 🔗 **No built-in JavaFX magic:** *UI updates?* You must manually marshal via `Platform.runLater(...)`!
- 🛠️ **Great for:** Running lots of background stuff, frequent jobs, and scheduled/repeating work.
- ⏲️ `ScheduledExecutorService` = built-in timer for recurring tasks.

✨ **Quick color-rule:**  
- 🟦 Use **Task** if you care about UI bindings, progress, or status.
- 🟩 Use **ExecutorService** for orchestration, pooling, and scheduling.  
- Often: 🟦 **Task** *runs* on 🟩 **ExecutorService** or a `Thread`.

⚡ **Pro-tip:**  
Combine them!  
> Run your `Task` inside an `ExecutorService` for best of both worlds.

Quick checklist:
- Long, slow, or blocking operations? Run **asynchronously** in a background thread (`Task`, `ExecutorService`)  
- Only ever touch the UI **synchronously on the FX thread** (marshal via `Platform.runLater`)  
- Repeating background work? Use `ScheduledExecutorService` (async) or `Timeline` (for strictly UI timing, sync within UI thread)  

## Patterns Used in the App (Synchronous vs. Asynchronous)

### 1) Background Fetch + UI Update (HTTP/DB) — Mostly Asynchronous

```java
Task<Void> t = new Task<>() {
  @Override protected Void call() throws Exception {
    // 1) This is asynchronous: executes in a background thread
    var price = stockPriceService.getCurrentPrice("AAPL");

    // 2) Marshal the result back to the UI thread (now sync with FX thread)
    Platform.runLater(() -> table.getItems().add(/* row built from price */));
    return null;
  }
};
new Thread(t, "http-fetch-aapl").start(); // Runs async
```

**Why:** By doing fetches async, the UI won’t freeze. Updates to the UI happen synchronously on the FX thread, guaranteeing UI safety.

### 2) Repeating work on an interval (polling, heartbeats) — Asynchronous + Sync for UI

```java
ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
ses.scheduleAtFixedRate(() -> {
  try {
    // Async: background thread for DB call
    var rows = kdbClient.exec("select sym,px from quote");
    // UI update must be synchronous on FX thread
    Platform.runLater(() -> updateTable(rows));
  } catch (Exception ignored) {}
}, 0, 1, TimeUnit.SECONDS); // Executes fully async at each interval
```

**Use cases:** polling real-time data from a database, periodic refresh.

- The scheduler and its jobs are **asynchronous**.
- Each run marshals brief, synchronous UI updates via `Platform.runLater`.

**Notes:**
- Keep scheduled (async) work quick—never block!
- If many UI controls must be updated, batch them inside one `Platform.runLater` (to make the UI updates synchronous and safe together).

### 3) JavaFX `Task` with progress/messages (For user feedback) — Async for work, Sync for UI bindings

```java
Task<Void> importTask = new Task<>() {
  @Override protected Void call() throws Exception {
    int n = files.size();
    for (int i = 0; i < n; i++) {
      // These methods are thread-safe; UI bindings listen and update synchronously on FX thread.
      updateMessage("Processing " + files.get(i));
      updateProgress(i + 1, n);
      process(files.get(i)); // Asynchronous processing
    }
    return null;
  }
};
progressBar.progressProperty().bind(importTask.progressProperty());     // UI updates happen synchronously on FX thread via bindings
statusLabel.textProperty().bind(importTask.messageProperty());          // Ditto
new Thread(importTask, "import-task").start();                          // Async start
```

### 4) UI animation vs. timers — Synchronous for Animation, Asynchronous for Timers

- For UI animation (opacity, translation, etc.):  
  Use JavaFX `Timeline` / `Animation` (**synchronous**, runs directly on the FX thread).
- For periodic polling/timers (non‑UI):  
  Use `ScheduledExecutorService` (**asynchronous**), then marshal results to UI using `Platform.runLater`.

## Useful APIs: Which are Asynchronous, Which Synchronous?

- `Platform.runLater(Runnable)`: schedules code to run **synchronously on the FX thread** (from any thread).
- `Task<T>`: encapsulates a background job — **executes asynchronously**, with thread-safe progress/messages for sync UI binding.
- `Service<T>`: reusable `Task` factory — **asynchronous** usage.
- `ExecutorService` / `ScheduledExecutorService`: runnables/futures on thread pools — **asynchronous** APIs.
- `CompletableFuture.supplyAsync(...)`: runs code asynchronously, supports chaining/continuations.

**Example:**

```java
// CompletableFuture: async background fetch
CompletableFuture.supplyAsync(() -> api.fetch())  // Async thread pool
  // thenAccept will run on fork/join thread by default — UI update must be marshaled:
  .thenAccept(result -> Platform.runLater(() -> apply(result))); // Sync UI update
```

## Concurrency Do’s and Don’ts (Sync vs. Async)

**Do:**
- Touch UI controls **only synchronously and only from the FX thread**.
- Always run API/DB/CPU-heavy work **asynchronously** on background threads.
- Batch multiple UI updates inside a single `runLater`, keeping FX-thread work synchronous and minimal.
- Name your threads (good for debugging asynchronous work).

**Don’t:**
- Never block the FX thread with long sleeps/loops/synchronous I/O.
- Don’t share mutable state between threads unless absolutely necessary — pass data from async → sync via `runLater`.

## Where This App Uses Asynchronous and Synchronous Patterns

- **Real‑Time Analysis window**
  - Connects to kdb+ and **asynchronously** polls every 1s using `ScheduledExecutorService`
  - Uses `Platform.runLater` to **synchronously** append rows to the `TableView` on the FX thread

- **Portfolio price refresh**
  - HTTP/kdb work runs **asynchronously** in the background (JavaFX `Task`)
  - UI feedback (alerts, labels) are updated **synchronously** using `runLater`

## Troubleshooting Concurrency

- **UI freezes** → A synchronous, long or blocking operation ran on the FX thread  
  - Solution: move blocking work **asynchronously** (via `Task`/`ExecutorService`)
- **Random `IllegalStateException: Not on FX application thread`** → UI accessed from background (async) thread  
  - Solution: wrap in `Platform.runLater` (to sync on FX thread)
- **Repeating tasks drift/overlap**  
  - Solution: keep async scheduled work short; use a single-thread scheduler to avoid overlaps

## Quick Snippets: Async vs. Sync

**Run once asynchronously in the background, then update UI synchronously:**
```java
new Thread(() -> {                         // Async task
  var r = work();
  Platform.runLater(() -> apply(r));       // Sync UI update
}, "bg-once").start();
```

**Repeat every N ms (asynchronous) via scheduler, sync for UI update:**
```java
ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
ses.scheduleAtFixedRate(() -> {            // Async task call
  try { var r = work(); 
        Platform.runLater(() -> apply(r)); // Sync on FX thread
  } catch (Exception ignored) {}
}, 0, N, TimeUnit.MILLISECONDS);
```

**Task with progress (asynchronous work, sync updates via bindings):**
```java
Task<Void> t = new Task<>() { protected Void call() throws Exception { /* updateProgress */ return null; } };
new Thread(t, "task").start();
```

By following these asynchronous and synchronous patterns, you can add new real-time features to your JavaFX app safely—without blocking the UI, and with predictable behavior.


