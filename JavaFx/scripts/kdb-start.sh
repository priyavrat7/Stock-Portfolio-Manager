#!/bin/bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
KDB_DIR="$ROOT_DIR/kdb"
LOG_DIR="$KDB_DIR/logs"
PID_DIR="$KDB_DIR/run"

mkdir -p "$LOG_DIR" "$PID_DIR"

Q_BIN="${Q_BIN:-q}"
if ! command -v "$Q_BIN" >/dev/null 2>&1; then
  echo "Error: q binary not found in PATH. Set Q_BIN=/path/to/q or add q to PATH." >&2
  exit 1
fi

start_one() {
  local name="$1"; shift
  local script="$1"; shift
  local log="$LOG_DIR/${name}.log"
  local pidfile="$PID_DIR/${name}.pid"

  if [ -f "$pidfile" ] && kill -0 "$(cat "$pidfile")" 2>/dev/null; then
    echo "$name already running (pid $(cat "$pidfile")). Skipping."
    return 0
  fi

  echo "Starting $name..."
  (cd "$KDB_DIR" && nohup "$Q_BIN" "$script" >"$log" 2>&1 & echo $! >"$pidfile")
  sleep 0.3
}

start_one rdb rdp.q
start_one feed feed.q

echo "Started kdb+ stack. Logs: $LOG_DIR | PIDs: $PID_DIR"
echo "- TP (5010): $KDB_DIR/tickerplant.q"
echo "- RDB (5012): $KDB_DIR/rdp.q"
echo "- FEED      : $KDB_DIR/feed.q"

exit 0


