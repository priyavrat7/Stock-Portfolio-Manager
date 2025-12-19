#!/bin/bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
PID_DIR="$ROOT_DIR/kdb/run"

stop_one() {
  local name="$1"
  local pidfile="$PID_DIR/${name}.pid"
  if [ -f "$pidfile" ]; then
    local pid
    pid=$(cat "$pidfile")
    if kill -0 "$pid" 2>/dev/null; then
      echo "Stopping $name (pid $pid)..."
      kill "$pid" || true
    fi
    rm -f "$pidfile"
  else
    echo "$name not running."
  fi
}

stop_one feed
stop_one rdb
stop_one tickerplant

echo "kdb+ stack stopped."
exit 0


